package com.demo.service.impl;

import com.demo.dto.request.CreateUserRequestDTO;
import com.demo.model.User;
import com.demo.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
          return userRepository.findAll();
    }

    public User getUser(int userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }

    public String createUser(CreateUserRequestDTO requestDTO){
        User user = new User();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setPhoneNo(requestDTO.getPhoneNo());
        user.setEmail(requestDTO.getEmail());
        user.setCity(requestDTO.getCity());
        userRepository.save(user);

        return "User Created";
    }
}
