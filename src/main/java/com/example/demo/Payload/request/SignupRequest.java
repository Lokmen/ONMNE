package com.example.demo.Payload.request;

import com.example.demo.Models.Role;

import java.util.Date;
import java.util.Set;

public class SignupRequest {
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String email;
    private Set<String> role;
    private Date datenes;

    public Date getDatenes() {
        return datenes;
    }

    public void setDatenes(Date datenes) {
        this.datenes = datenes;
    }

    public SignupRequest() {
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

    public SignupRequest(String username, String password, String email, Set<String> role, Date datenes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.datenes = datenes;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
