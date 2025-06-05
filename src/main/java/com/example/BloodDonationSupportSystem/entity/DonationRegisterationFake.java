package com.example.BloodDonationSupportSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donation_registration_fake")
public class DonationRegisterationFake {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "donation_registration_id", columnDefinition = "uniqueidentifier")
    private UUID donation_registration_id;

    @Column(name = "donation_name", columnDefinition = "nvarchar(100)")
    @NotNull
    private String donation_name;

    @Column(name = "status", columnDefinition = "nvarchar(50)")
    @NotNull
    private String status;
}
