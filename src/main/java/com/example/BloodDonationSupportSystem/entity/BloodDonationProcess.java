package com.example.BloodDonationSupportSystem.entity;

import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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

    public UUID getDonation_process_id() {
        return donation_process_id;
    }

    public void setDonation_process_id(UUID donation_process_id) {
        this.donation_process_id = donation_process_id;
    }

    public MemberScreening getScreening_id() {
        return screening_id;
    }

    public void setScreening_id(MemberScreening screening_id) {
        this.screening_id = screening_id;
    }

    public int getVolumeBloodCollected() {
        return volumeBloodCollected;
    }

    public void setVolumeBloodCollected(int volumeBloodCollected) {
        this.volumeBloodCollected = volumeBloodCollected;
    }

    public BloodDonationProcessStatus getStatus() {
        return status;
    }

    public void setStatus(BloodDonationProcessStatus status) {
        this.status = status;
    }
}
