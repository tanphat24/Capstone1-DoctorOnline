package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private long id;
    private String role;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private boolean status;

    private RoleDTO roleDTO;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
