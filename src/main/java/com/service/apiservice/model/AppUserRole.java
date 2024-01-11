package com.service.apiservice.model;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_SALES, ROLE_STAFF, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}
