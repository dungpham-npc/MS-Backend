package com.cookswp.milkstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private int roleId;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String username;
}
