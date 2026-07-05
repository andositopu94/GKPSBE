package com.GKPS.Controller;

import com.GKPS.Model.Enum.RoleType;
import com.GKPS.Model.User;
import com.GKPS.Service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller untuk User Management dengan Advanced Security
 * Endpoint untuk manage roles dan permissions
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserManagementController {
    private final UserManagementService userManagementService;

    /**
     * Assign role ke user
     * POST /api/admin/users/{username}/roles
     * Body: { "role": "KETUA_SEKSI" }
     */
    @PostMapping("/{username}/roles")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA')")
    public ResponseEntity<?> assignRole(
            @PathVariable String username,
            @RequestBody Map<String, String> roleRequest,
            Authentication authentication) {

        String roleStr = roleRequest.get("role");
        if (roleStr == null || roleStr.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Role is required"));
        }

        try {
            RoleType roleToAssign = RoleType.valueOf(roleStr.toUpperCase());
            String requestingUser = authentication.getName();

            User updatedUser = userManagementService.assignRoleToUser(username, roleToAssign, requestingUser);
            return ResponseEntity.ok(Map.of(
                    "message", "Role assigned successfully",
                    "user", updatedUser.getUsername(),
                    "roles", updatedUser.getRoles()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role: " + roleStr));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Revoke role dari user
     * DELETE /api/admin/users/{username}/roles/{role}
     */
    @DeleteMapping("/{username}/roles/{role}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA')")
    public ResponseEntity<?> revokeRole(
            @PathVariable String username,
            @PathVariable String role,
            Authentication authentication) {

        try {
            RoleType roleToRevoke = RoleType.valueOf(role.toUpperCase());
            String requestingUser = authentication.getName();

            User updatedUser = userManagementService.revokeRoleFromUser(username, roleToRevoke, requestingUser);
            return ResponseEntity.ok(Map.of(
                    "message", "Role revoked successfully",
                    "user", updatedUser.getUsername(),
                    "roles", updatedUser.getRoles()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role: " + role));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Upgrade role user
     * POST /api/admin/users/{username}/upgrade
     * Body: { "newRole": "KETUA_SEKSI" }
     */
    @PostMapping("/{username}/upgrade")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA')")
    public ResponseEntity<?> upgradeUserRole(
            @PathVariable String username,
            @RequestBody Map<String, String> roleRequest,
            Authentication authentication) {

        String newRoleStr = roleRequest.get("newRole");
        if (newRoleStr == null || newRoleStr.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "New role is required"));
        }

        try {
            RoleType newRole = RoleType.valueOf(newRoleStr.toUpperCase());
            String requestingUser = authentication.getName();

            User updatedUser = userManagementService.upgradeUserRole(username, newRole, requestingUser);
            return ResponseEntity.ok(Map.of(
                    "message", "User role upgraded successfully",
                    "user", updatedUser.getUsername(),
                    "roles", updatedUser.getRoles()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role: " + newRoleStr));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Check apakah user memiliki role tertentu
     * GET /api/admin/users/{username}/has-role/{role}
     */
    @GetMapping("/{username}/has-role/{role}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'KETUA_SEKSI', 'KETUA_SEKTOR')")
    public ResponseEntity<?> checkUserRole(
            @PathVariable String username,
            @PathVariable String role) {

        try {
            RoleType roleToCheck = RoleType.valueOf(role.toUpperCase());
            boolean hasRole = userManagementService.userHasRole(username, roleToCheck);

            return ResponseEntity.ok(Map.of(
                    "username", username,
                    "role", role,
                    "hasRole", hasRole
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role: " + role));
        }
    }

    /**
     * Disable user account
     * POST /api/admin/users/{username}/disable
     */
    @PostMapping("/{username}/disable")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA')")
    public ResponseEntity<?> disableUser(
            @PathVariable String username,
            Authentication authentication) {

        try {
            String requestingUser = authentication.getName();
            userManagementService.disableUserAccount(username, requestingUser);

            return ResponseEntity.ok(Map.of(
                    "message", "User account disabled successfully",
                    "username", username
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Enable user account
     * POST /api/admin/users/{username}/enable
     */
    @PostMapping("/{username}/enable")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA')")
    public ResponseEntity<?> enableUser(
            @PathVariable String username,
            Authentication authentication) {

        try {
            String requestingUser = authentication.getName();
            userManagementService.enableUserAccount(username, requestingUser);

            return ResponseEntity.ok(Map.of(
                    "message", "User account enabled successfully",
                    "username", username
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
