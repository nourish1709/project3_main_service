package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.models.Account;

public interface AccountInterface {

    Account update(Long id, Account account);

    void setNotifications(Long id, boolean enabledNotifications);

    Account getById(Long id);
}
