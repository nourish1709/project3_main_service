package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.models.dto.AccountDto;

public interface AccountInterface {
    AccountDto update(Long id, AccountDto accountDto);

    void setNotifications(Boolean enabledNotifications);

    AccountDto getById(Long id);
}
