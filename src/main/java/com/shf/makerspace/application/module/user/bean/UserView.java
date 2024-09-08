package com.shf.makerspace.application.module.user.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String profileUrl;
    private String createdDate;
    private Boolean isActive;
    private GenericDropdownView userType;
}
