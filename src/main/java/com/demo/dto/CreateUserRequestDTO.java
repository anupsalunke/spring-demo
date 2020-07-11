package com.demo.dto;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String city;
}
