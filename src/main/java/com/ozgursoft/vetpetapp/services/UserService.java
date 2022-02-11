package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.Repository.UserRepository;
import com.ozgursoft.vetpetapp.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public void save(User user) {

        // user.setRoles(Set.of(new Role("USER","It can't just delete. Admin is also a user, he can delete.")));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(int id) {
        Optional<User> optional = userRepository.findById(id);
        User user;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" user not found for id :: " + id);
        }
        return user;
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }


}