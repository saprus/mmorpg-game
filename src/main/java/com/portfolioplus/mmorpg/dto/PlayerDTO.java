package com.portfolioplus.mmorpg.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolioplus.mmorpg.model.Role;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;

    public PlayerDTO() {
    }

    public PlayerDTO(Long id, String username, String password, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //@JsonIgnore
    public String getPassword() {
        return password != null ? "********" : null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
