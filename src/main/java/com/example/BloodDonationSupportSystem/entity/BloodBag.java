package com.example.BloodDonationSupportSystem.entity;

import com.example.BloodDonationSupportSystem.enumentity.BloodTypeEnum;
import com.example.BloodDonationSupportSystem.enumentity.StatusBloodBagEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Check;
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
    @Enumerated(EnumType.STRING)
    private BloodTypeEnum bloodType;

    @Column(name = "volume_ml")
    private int volume;

    @Column(name="amount_bag")
    private int amountBag;

    @Column(name = " created_at")
    private LocalDate createdAt;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusBloodBagEnum status;

//    @OneToOne
//    @JoinColumn(name = "donation_registration_id")
//    private DonationRegisteration donationRegisteration;
}
