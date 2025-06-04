package com.example.BloodDonationSupportSystem.service.processmanagement;

import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCompletedRequest;
import com.example.BloodDonationSupportSystem.entity.BloodDonationProcess;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BloodDonationProcessService {
     List<BloodDonationProcess> findByStatus(BloodDonationProcessStatus status);
     String updateToCompleted(UpdateToCompletedRequest req);

}

