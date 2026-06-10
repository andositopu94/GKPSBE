package com.GKPS.Service;

import com.GKPS.Model.CustomUserDetails;
import com.GKPS.Model.User;
import com.GKPS.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        if (!user.getEnabled()){
            throw new UsernameNotFoundException("User account is disabled: " + usernameOrEmail);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(String id)  {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (!user.getEnabled()){
            throw new UsernameNotFoundException("User account is disabled: " + id);
        }
        return new CustomUserDetails(user);
    }
}
