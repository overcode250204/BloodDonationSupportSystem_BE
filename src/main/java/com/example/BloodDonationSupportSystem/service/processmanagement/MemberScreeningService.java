package com.example.BloodDonationSupportSystem.service.processmanagement;


import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCollectingRequest;
import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToScreeningRequest;
import com.example.BloodDonationSupportSystem.entity.MemberScreening;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.MemberScreeningStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberScreeningService {
    List<MemberScreening> findByStatus(MemberScreeningStatus status);
    String updateToScreening(UpdateToScreeningRequest req);
    String updateToCollecting(UpdateToCollectingRequest req);
}
