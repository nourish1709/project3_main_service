package com.nourish1709.project3_main_service.security.enums;

public enum UserAuthority {
    ROLE_ACCOUNT_READ("account:read"),
    ROLE_ACCOUNT_WRITE("account_write"),
    ROLE_NOTIFICATION_WRITE("notification:write"),
    ROLE_CATEGORY_READ("category_read"),
    ROLE_CATEGORY_WRITE("category_write"),
    ROLE_POSITION_READ("position:read"),
    ROLE_POSITION_WRITE("position:write");

    private final String authority;

    UserAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
