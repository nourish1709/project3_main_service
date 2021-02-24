package com.nourish1709.project3_main_service.models.dto;

import com.nourish1709.project3_main_service.models.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private User user;
    private boolean enabledNotifications;

}
