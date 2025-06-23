package com.example.BloodDonationSupportSystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "donation_emergency")
public class DonationEmergencyEntity {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "donation_emergency_id", columnDefinition = "uniqueidentifier")
    private UUID donationEmergencyId;

    @Column(name = "assigned_date")
    private Date assignedDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "emergency_blood_request_id")
    private EmergencyBloodRequestEntity emergencyBloodRequest;

    @ManyToOne
    @JoinColumn(name = "donation_registration_id")
    private DonationRegistrationEntity donationRegistration;


}
