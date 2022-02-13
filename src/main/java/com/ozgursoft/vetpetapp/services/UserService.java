package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.exception.VetPetRuntimeException;
import com.ozgursoft.vetpetapp.repository.UserRepository;
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
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(int id) {
        Optional<User> optional = userRepository.findById(id);
        User user;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new VetPetRuntimeException(" user not found for id :: " + id);
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