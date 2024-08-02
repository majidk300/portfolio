package com.portfolio.portfolio.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class adminEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int adminId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public adminEntity(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public adminEntity() {
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "adminEntity [adminId=" + adminId + ", email=" + email + ", password=" + password + ", role=" + role
                + "]";
    }

}
