package com.example.demo.Models;

import jakarta.persistence.*;

import java.util.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="User")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class UserModel {
    @Id
    @GeneratedValue(strategy =GenerationType.TABLE )
    private Long Id;
    private String username;
    private String email;
    private String password;
    private String photo;
    private Date datenes;
    private boolean confirm ;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();

    private String passwordResetToken;

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserModel() {
    }
    public boolean isConfirm() {
        return confirm;
    }
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public UserModel(Long id, String username, String email, String password, String photo, Date datenes) {
        Id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.datenes = datenes;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDatenes() {
        return datenes;
    }

    public void setDatenes(Date datenes) {
        this.datenes = datenes;
    }

}
