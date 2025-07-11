package com.example.BloodDonationSupportSystem.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauthaccount")
public class OauthAccountEntity {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "oauthaccount_id", columnDefinition = "uniqueidentifier")
    private UUID oauthAccountId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_user_id")
    private String providerUserId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "account")
    private String account;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}

