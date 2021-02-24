package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.exceptions.*;
import com.nourish1709.project3_main_service.models.Account;

public interface AccountInterface {

    Account update(Account account)
            throws AccountNotFoundException,
            BadCredentialsException,
            InvalidNameException,
            InvalidPhoneException,
            InvalidAgeException,
            PhoneNumberIsAlreadyTakenException;

    void setNotifications(Long id, boolean enabledNotifications)
            throws AccountNotFoundException;

    Account getById(Long id)
            throws AccountNotFoundException;
}
