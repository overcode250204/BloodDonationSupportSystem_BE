package com.example.BloodDonationSupportSystem.entity;

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

}




