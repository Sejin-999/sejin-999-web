package com.sejin999.domain.user.domain;

public enum UserRole {
    USER("basic_user"),
    ADMIN("admin_user");

    private final String roleName;
    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
