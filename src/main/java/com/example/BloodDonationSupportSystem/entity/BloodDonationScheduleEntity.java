package com.example.BloodDonationSupportSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "blood_donation_schedule")
public class BloodDonationScheduleEntity {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "blood_donation_schedule_id", columnDefinition = "uniqueidentifier")
    private UUID bloodDonationScheduleId;

    @Column(name = "address_hospital")
    private String addressHospital;

    @Column(name = "donation_date")
    private LocalDate donationDate;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "amount_registration")
    private int amountRegistration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_by_staff")
    private UserEntity editedByStaffId;

    @OneToMany(mappedBy = "bloodDonationSchedule", fetch = FetchType.LAZY)
    private List<DonationRegistrationEntity> donationRegistrations;


}
