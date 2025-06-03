package com.example.BloodDonationSupportSystem.entity;


import com.example.BloodDonationSupportSystem.enumentity.processmanagement.HealthStatusEnum;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.MemberScreeningStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "member_screening")
public class MemberScreening {

    @Id
    @Column(name = "member_screening_id")
    private UUID member_screening_id;

    @Column(name = "screening_date")
    private LocalDateTime screening_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_status")
    private HealthStatusEnum health_status;

    @Column(name = "updated_blood_type")
    private String updated_blood_type;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MemberScreeningStatus status;

    @Column(name = "donation_registration_id")
    private UUID donation_registration_id;

    @Column(name = "screened_by_staff_id")
    private UUID screened_by_staff_id;

    public void setStatus(MemberScreeningStatus status) {
        this.status = status;
    }

    public MemberScreeningStatus getStatus() {
        return status;
    }

    public HealthStatusEnum getHealth_status() {
        return health_status;
    }

    public void setHealth_status(HealthStatusEnum health_status) {
        this.health_status = health_status;
    }
}
