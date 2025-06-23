package com.example.BloodDonationSupportSystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "donation_history")
public class DonationHistoryEntity {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "donation_history_id", columnDefinition = "uniqueidentifier")
    private UUID donationHistoryId;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "address_hospital")
    private String addressHospital;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private UserEntity donorHistory;

    @ManyToOne
    @JoinColumn(name = "donation_registration_id")
    private DonationRegistrationEntity donationRegistration;

}
