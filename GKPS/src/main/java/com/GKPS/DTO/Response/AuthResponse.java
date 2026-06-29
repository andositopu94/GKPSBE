package com.GKPS.DTO.Response;

import com.GKPS.Model.Enum.RoleType;

import java.util.Set;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private String username;
    private String email;
    private String fullName;
    private String gerejaId;
    private String gerejaName;
    private Set<RoleType> roles;
    private Long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGerejaId() {
        return gerejaId;
    }

    public void setGerejaId(String gerejaId) {
        this.gerejaId = gerejaId;
    }

    public String getGerejaName() {
        return gerejaName;
    }

    public void setGerejaName(String gerejaName) {
        this.gerejaName = gerejaName;
    }

    public Set<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
