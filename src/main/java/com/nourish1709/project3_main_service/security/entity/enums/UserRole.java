package com.nourish1709.project3_main_service.security.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.nourish1709.project3_main_service.security.entity.enums.UserAuthority.*;

public enum UserRole {
    ADMIN(Set.of(
            NOTIFICATION_WRITE,
            ACCOUNT_READ,
            ACCOUNT_WRITE,
            CATEGORY_WRITE,
            POSITION_WRITE)
    ),
    USER(Set.of(
            NOTIFICATION_WRITE,
            CATEGORY_READ,
            POSITION_READ)
    );

    private final Set<UserAuthority> authorities;


    UserRole(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}


