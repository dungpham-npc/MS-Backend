package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_Number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "visibility_status")
    private boolean visibilityStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "otp_created_at")
    private String otpCreatedAt;

    @Transient
    public String getRoleName() {
        return role != null ? role.getRoleName() : null;
    }

    public User(int userId, String emailAddress, String phoneNumber, String password, String username) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
    }
}
