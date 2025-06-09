package com.example.BloodDonationSupportSystem.entity;

import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="blood_donation_process")
public class BloodDonationProcess {

    @Id
    @Column(name = "donation_process_id")
    private UUID donation_process_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BloodDonationProcessStatus status;

    @Column(name = "volume_blood_collected")
    private int volumeBloodCollected;

    @OneToOne
    @JoinColumn(name = "member_screening_id")
    private MemberScreening screening_id;

}
