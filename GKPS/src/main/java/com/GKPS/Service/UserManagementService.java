package com.GKPS.Service;

import com.GKPS.Model.Enum.RoleType;
import com.GKPS.Model.User;
import com.GKPS.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    private final UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Assign role kepada user dengan validasi business logic
     * - Hanya MAJELIS atau PENDETA yang bisa assign role
     * - Validasi hierarki role (tidak bisa assign role lebih tinggi dari diri sendiri)
     */
    public User assignRoleToUser(String targetUsername, RoleType roleToAssign, String requestingUsername) {
        // Validasi requestor memiliki permission
        validateRoleAssignmentPermission(requestingUsername, roleToAssign);

        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUsername));

        Set<RoleType> currentRoles = targetUser.getRoles();

        if (currentRoles.contains(roleToAssign)) {
            logger.warn("User {} already has role {}", targetUsername, roleToAssign);
            throw new RuntimeException("User already has this role");
        }

        currentRoles.add(roleToAssign);
        targetUser.setRoles(currentRoles);
        targetUser.preUpdate();

        User savedUser = userRepository.save(targetUser);
        logger.info("Role {} assigned to user {} by {}", roleToAssign, targetUsername, requestingUsername);

        return savedUser;
    }

    /**
     * Revoke role dari user dengan validasi business logic
     */
    public User revokeRoleFromUser(String targetUsername, RoleType roleToRevoke, String requestingUsername) {
        // Validasi requestor memiliki permission
        validateRoleAssignmentPermission(requestingUsername, roleToRevoke);

        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUsername));

        Set<RoleType> currentRoles = targetUser.getRoles();

        if (!currentRoles.contains(roleToRevoke)) {
            logger.warn("User {} does not have role {}", targetUsername, roleToRevoke);
            throw new RuntimeException("User does not have this role");
        }

        // Validasi: tidak bisa revoke role terakhir jika itu adalah role dasar
        if (currentRoles.size() == 1) {
            throw new RuntimeException("Cannot revoke the last role from user");
        }

        currentRoles.remove(roleToRevoke);
        targetUser.setRoles(currentRoles);
        targetUser.preUpdate();

        User savedUser = userRepository.save(targetUser);
        logger.info("Role {} revoked from user {} by {}", roleToRevoke, targetUsername, requestingUsername);

        return savedUser;
    }

    /**
     * Upgrade role user (misal: dari ANGGOTA ke KETUA_SEKSI)
     * Dengan validasi hierarki
     */
    public User upgradeUserRole(String targetUsername, RoleType newRole, String requestingUsername) {
        validateRoleAssignmentPermission(requestingUsername, newRole);

        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUsername));

        Set<RoleType> currentRoles = targetUser.getRoles();

        // Tentukan role utama saat ini
        RoleType currentMainRole = determineMainRole(currentRoles);

        // Validasi upgrade hanya ke role yang lebih tinggi
        if (!isRoleHigher(newRole, currentMainRole)) {
            throw new RuntimeException("New role is not higher than current role");
        }

        currentRoles.add(newRole);
        targetUser.setRoles(currentRoles);
        targetUser.preUpdate();

        User savedUser = userRepository.save(targetUser);
        logger.info("User {} upgraded to role {} by {}", targetUsername, newRole, requestingUsername);

        return savedUser;
    }

    /**
     * Check apakah user memiliki role tertentu
     */
    public boolean userHasRole(String username, RoleType role) {
        return userRepository.findByUsername(username)
                .map(user -> user.getRoles().contains(role))
                .orElse(false);
    }

    /**
     * Get semua user dengan role tertentu
     */
    public Set<User> getUsersByRole(RoleType role) {
        // TODO: Implement repository method to find users by role
        // For now, return empty set
        return Set.of();
    }

    /**
     * Validate apakah user yang login memiliki permission untuk assign role
     */
    private void validateRoleAssignmentPermission(String requestingUsername, RoleType roleToAssign) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Unauthenticated user attempted to assign role");
            throw new AccessDeniedException("Authentication required");
        }

        String currentUser = authentication.getName();

        // Jika requestor bukan yang login, log warning
        if (!currentUser.equals(requestingUsername)) {
            logger.warn("User {} attempting action on behalf of {}", currentUser, requestingUsername);
        }

        // Dapatkan role user yang sedang login
        Set<RoleType> userRoles = getCurrentUserRoles();

        if (userRoles.isEmpty()) {
            logger.warn("User {} has no roles", currentUser);
            throw new AccessDeniedException("User has no roles assigned");
        }

        RoleType mainRole = determineMainRole(userRoles);

        // Validasi berdasarkan hierarki role
        switch (roleToAssign) {
            case PENDETA:
                // Hanya PENDETA lain atau sistem yang bisa assign role PENDETA
                if (mainRole != RoleType.PENDETA) {
                    throw new AccessDeniedException("Only PENDETA can assign PENDETA role");
                }
                break;

            case MAJELIS:
                // Hanya PENDETA atau MAJELIS yang bisa assign role MAJELIS
                if (mainRole != RoleType.PENDETA && mainRole != RoleType.MAJELIS) {
                    throw new AccessDeniedException("Only PENDETA or MAJELIS can assign MAJELIS role");
                }
                break;

            case KETUA_SEKSI:
            case KETUA_SEKTOR:
                // MAJELIS atau PENDETA yang bisa assign
                if (mainRole != RoleType.PENDETA && mainRole != RoleType.MAJELIS) {
                    throw new AccessDeniedException("Only MAJELIS or PENDETA can assign leadership roles");
                }
                break;

            default:
                // Role lainnya bisa diassign oleh minimal KETUA_SEKSI atau MAJELIS
                if (mainRole != RoleType.PENDETA &&
                        mainRole != RoleType.MAJELIS &&
                        mainRole != RoleType.KETUA_SEKSI &&
                        mainRole != RoleType.KETUA_SEKTOR) {
                    throw new AccessDeniedException("Insufficient privileges to assign this role");
                }
                break;
        }
    }

    /**
     * Determine main/primary role dari set roles
     * Mengembalikan role dengan prioritas tertinggi
     */
    private RoleType determineMainRole(Set<RoleType> roles) {
        if (roles.contains(RoleType.PENDETA)) return RoleType.PENDETA;
        if (roles.contains(RoleType.MAJELIS)) return RoleType.MAJELIS;
        if (roles.contains(RoleType.SINTUA)) return RoleType.SINTUA;
        if (roles.contains(RoleType.SYAMAS)) return RoleType.SYAMAS;
        if (roles.contains(RoleType.KETUA_SEKSI)) return RoleType.KETUA_SEKSI;
        if (roles.contains(RoleType.KETUA_SEKTOR)) return RoleType.KETUA_SEKTOR;
        if (roles.contains(RoleType.SEKRETARIS_SEKSI)) return RoleType.SEKRETARIS_SEKSI;
        if (roles.contains(RoleType.BENDAHARA_SEKSI)) return RoleType.BENDAHARA_SEKSI;
        return RoleType.ANGGOTA;
    }

    /**
     * Check apakah role1 lebih tinggi dari role2 dalam hierarki
     */
    private boolean isRoleHigher(RoleType role1, RoleType role2) {
        // Simple implementation - can be enhanced with proper hierarchy map
        int priority1 = getRolePriority(role1);
        int priority2 = getRolePriority(role2);
        return priority1 > priority2;
    }

    /**
     * Get priority value untuk role (semakin tinggi semakin penting)
     */
    private int getRolePriority(RoleType role) {
        return switch (role) {
            case PENDETA -> 100;
            case MAJELIS -> 90;
            case SINTUA -> 80;
            case SYAMAS -> 80;
            case KETUA_SEKSI, KETUA_SEKTOR -> 70;
            case SEKRETARIS_SEKSI, SEKRETARIS_SEKTOR -> 60;
            case BENDAHARA_SEKSI, BENDAHARA_SEKTOR -> 50;
            case SEKSI_BAPA, SEKSI_WANITA, SEKSI_PEMUDA, SEKSI_REMAJA, SEKSI_SEKOLAH_MINGGU -> 40;
            case ANGGOTA -> 30;
            case JEMAAT -> 20;
            case LAINNYA -> 10;
        };
    }

    /**
     * Get roles dari user yang sedang login
     */
    private Set<RoleType> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Set.of();
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(User::getRoles)
                .orElse(Set.of());
    }

    /**
     * Soft delete user (disable account)
     */
    public void disableUserAccount(String targetUsername, String requestingUsername) {
        validateRoleAssignmentPermission(requestingUsername, RoleType.JEMAAT);

        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUsername));

        targetUser.setEnabled(false);
        targetUser.preUpdate();

        userRepository.save(targetUser);
        logger.info("User account disabled: {} by {}", targetUsername, requestingUsername);
    }

    /**
     * Enable kembali user account
     */
    public void enableUserAccount(String targetUsername, String requestingUsername) {
        validateRoleAssignmentPermission(requestingUsername, RoleType.JEMAAT);

        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUsername));

        targetUser.setEnabled(true);
        targetUser.preUpdate();

        userRepository.save(targetUser);
        logger.info("User account enabled: {} by {}", targetUsername, requestingUsername);
    }

}
