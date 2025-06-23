package com.example.BloodDonationSupportSystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity(name = "health_check")
public class HealthCheckEntity {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "health_check_id", columnDefinition = "uniqueidentifier")
    private UUID healthCheckId;

    @Column(name = "height")
    private float height;

    @Column(name = "weight")
    private float weight;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "health_status")
    private String healthStatus;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(name = "donation_registration_id")
    private DonationRegistrationEntity donationRegistrationHealthCheck;

}
