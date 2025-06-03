package com.example.BloodDonationSupportSystem.dto.processmanagement;


import com.example.BloodDonationSupportSystem.enumentity.processmanagement.HealthStatusEnum;

import java.util.UUID;

public class UpdateToCollectingRequest {
    private UUID member_screening_id;
    private HealthStatusEnum health_status;

    public UUID getMember_screening_id() {
        return member_screening_id;
    }

    public void setMember_screening_id(UUID member_screening_id) {
        this.member_screening_id = member_screening_id;
    }

    public HealthStatusEnum getHealth_status() {
        return health_status;
    }

    public void setHealth_status(HealthStatusEnum health_status) {
        this.health_status = health_status;
    }
}
