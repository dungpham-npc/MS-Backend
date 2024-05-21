package com.cookswp.milkstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", nullable = false)
    private String role;

    public int getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleId=" + roleId +
                ", role=" + role +
                '}';
    }
}
