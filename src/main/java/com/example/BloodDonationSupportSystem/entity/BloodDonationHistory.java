package com.example.BloodDonationSupportSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="blood_donation_history")
public class BloodDonationHistory {

    @Id
    @Column(name = "blood_donation_history_id")
    private UUID blood_donation_history_id;

    @Column(name = "donation_date")
    private LocalDateTime donation_date;

    @Column(name = "volume_ml")
    private int volume_ml;

    @OneToOne
    @JoinColumn(name = "donation_process_id")
    private com.example.BloodDonationSupportSystem.entity.BloodDonationProcess donation_process_id;

    public UUID getBlood_donation_history_id() {
        return blood_donation_history_id;
    }

    public void setBlood_donation_history_id(UUID blood_donation_history_id) {
        this.blood_donation_history_id = blood_donation_history_id;
    }

    public BloodDonationProcess getDonation_process_id() {
        return donation_process_id;
    }

    public void setDonation_process_id(BloodDonationProcess donation_process_id) {
        this.donation_process_id = donation_process_id;
    }

    public int getVolume_ml() {
        return volume_ml;
    }

    public void setVolume_ml(int volume_ml) {
        this.volume_ml = volume_ml;
    }

    public LocalDateTime getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(LocalDateTime donation_date) {
        this.donation_date = donation_date;
    }
}




