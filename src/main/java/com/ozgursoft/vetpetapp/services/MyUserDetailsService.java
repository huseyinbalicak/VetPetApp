package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.exception.VetPetRuntimeException;
import com.ozgursoft.vetpetapp.repository.UserRepository;
import com.ozgursoft.vetpetapp.model.UserPrincipal;
import com.ozgursoft.vetpetapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new VetPetRuntimeException(" user not found");
        }

        return new UserPrincipal(user);
    }
}
