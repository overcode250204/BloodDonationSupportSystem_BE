package com.example.BloodDonationSupportSystem.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth_account")
public class OauthAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_account_id")
    private Long oauthAccountId; // 🔐 Primary Key nội bộ

    @Column(name = "provider")
    private String provider; // eg: "google"

    @Column(name = "provider_user_id")
    private String providerUserId; // eg: "1234567890"

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}

