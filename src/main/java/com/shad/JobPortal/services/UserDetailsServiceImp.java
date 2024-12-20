package com.shad.JobPortal.services;

import com.shad.JobPortal.entity.User;
import com.shad.JobPortal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
                    .password(user.getPassword()).roles(user.getRoles().
                            toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
