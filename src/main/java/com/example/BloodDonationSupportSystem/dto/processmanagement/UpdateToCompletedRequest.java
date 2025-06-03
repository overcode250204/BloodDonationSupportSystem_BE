package com.example.BloodDonationSupportSystem.dto.processmanagement;

import java.util.UUID;

public class UpdateToCompletedRequest {
    private UUID donation_process_id;
    private Integer volume_ml;

    public UUID getDonation_process_id() {
        return donation_process_id;
    }

    public void setDonation_process_id(UUID donation_process_id) {
        this.donation_process_id = donation_process_id;
    }

    public Integer getVolume_ml() {
        return volume_ml;
    }

    public void setVolume_ml(Integer volume_ml) {
        this.volume_ml = volume_ml;
    }
}
