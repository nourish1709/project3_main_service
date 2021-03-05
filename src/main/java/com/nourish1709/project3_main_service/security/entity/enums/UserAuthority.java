package com.nourish1709.project3_main_service.security.entity.enums;

public enum UserAuthority {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account_write"),
    NOTIFICATION_WRITE("notification:write"),
    CATEGORY_READ("category_read"),
    CATEGORY_WRITE("category_write"),
    POSITION_READ("position:read"),
    POSITION_WRITE("position:write");

    private final String authority;

    UserAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
