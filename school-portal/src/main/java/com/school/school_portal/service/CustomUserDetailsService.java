package com.school.school_portal.service;

import com.school.school_portal.entity.User;
import com.school.school_portal.repository.UserRepository;
import com.school.school_portal.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository usersRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.usersRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                "Could not found user"
        ));

        return new CustomUserDetails(user);
    }
}
