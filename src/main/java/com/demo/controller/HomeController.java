package com.demo.controller;

import com.demo.dto.BaseResponseDTO;
import com.demo.dto.request.CreateUserRequestDTO;
import com.demo.model.User;
import com.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1.0/home/")
public class HomeController{

    @Autowired
    private UserService userService;

    @GetMapping(
            value = "/user/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponseDTO<User> getSystemData(
            @RequestParam(value = "userId", required = true) int userId) {

        BaseResponseDTO<User> response = new BaseResponseDTO<>();
        response.setData(userService.getUser(userId));
        return response;
    }

    @GetMapping(
            value = "/users/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponseDTO<List<User>> getAllUsers() {

        BaseResponseDTO<List<User>> response = new BaseResponseDTO<>();
        response.setData(userService.getAllUsers());
        return response;
    }

    @PostMapping(
            value = "/user/create",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponseDTO<CreateUserRequestDTO> createUser(
            @RequestBody CreateUserRequestDTO requestDTO) {

        BaseResponseDTO<CreateUserRequestDTO> response = new BaseResponseDTO<>();
        response.setMessage(userService.createUser(requestDTO));
        return response;
    }
}