package com.example.BloodDonationSupportSystem.dto.processmanagement;

import java.util.UUID;

public class UpdateToScreeningRequest {
    UUID member_screening_id;

    public UUID getMember_screening_id() {
        return member_screening_id;
    }

    public void setMember_screening_id(UUID member_screening_id) {
        this.member_screening_id = member_screening_id;
    }
}
