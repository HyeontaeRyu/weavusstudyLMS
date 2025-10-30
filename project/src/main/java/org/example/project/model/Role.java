package org.example.project.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_STUDENT("受講者"),
    ROLE_TEACHER("講師"),
    ROLE_ADMIN("管理者");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getAuthority() {
        return name();
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
