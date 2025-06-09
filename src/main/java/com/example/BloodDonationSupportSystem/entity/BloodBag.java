package com.example.BloodDonationSupportSystem.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "blood_bag")
public class BloodBag {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "blood_bag_id", columnDefinition = "uniqueidentifier")
    private UUID bloodBagId;

    @Column(name = "bloodType")
    private String bloodType;

    @Column(name = "volume_ml")
    private int volume;

    @Column(name="amount_bag")
    private int amountBag;

    @Column(name = " created_at")
    private LocalDate createdAt;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "status")
    private String status;

//    @OneToOne
//    @JoinColumn(name = "donation_registration_id")
//    private DonationRegisteration donationRegisteration;
}
