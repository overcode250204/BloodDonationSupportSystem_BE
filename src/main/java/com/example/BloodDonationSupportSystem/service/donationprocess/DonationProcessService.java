package com.example.BloodDonationSupportSystem.service.donationprocess;

import com.example.BloodDonationSupportSystem.dto.donationprocessDTO.request.DonationProcessRequest;
import com.example.BloodDonationSupportSystem.dto.donationprocessDTO.response.DonationProcessResponse;
import com.example.BloodDonationSupportSystem.dto.healthcheckDTO.response.HealthCheckResponse;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.DonationProcessRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DonationProcessService {

    @Autowired
    private DonationProcessRepository donationProcessRepository;

    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    public List<DonationProcessResponse> getDonationProcessByStaffId(UUID staffId){
        List<Object[]> donationProcesses = donationProcessRepository.findDonationProcessByStaffId(staffId);

        return donationProcesses.stream().map(row -> new DonationProcessResponse(
                UUID.fromString(row[0].toString()), // donation_process_id
                row[1].toString(),                  // donor full name
                LocalDate.parse(row[2].toString()),                 // registration_date
                row[3] != null ? row[3].toString() : null, // urgency
                row[4].toString(),                  // status
                row[5].toString(),                  // process_status
                row[6] != null ? row[6].toString() : null,                  // note
                UUID.fromString(row[7].toString()), // donation_registration_id
                UUID.fromString(row[8].toString())  // screened_by_staff_id
        )).toList();
    }

    @Transactional
    public void updateDonationProcess(DonationProcessRequest request){
        DonationProcessEntity process = donationProcessRepository.findById(request.getDonationProcessId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found."));

        process.setStatus(request.getProcessStatus());

        if ("ĐÃ HIẾN".equals(request.getProcessStatus())) {
            process.setVolumeMl(request.getVolumeMl());
            process.setBloodTest("CHƯA KIỂM TRA");

            // Cập nhật trạng thái đơn đăng ký
            DonationRegistrationEntity registration = donationRegistrationRepository.findById(request.getDonationRegistrationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found."));
            registration.setStatus("ĐÃ HIẾN");
            registration.setDateCompleteDonation(LocalDate.now());
            donationRegistrationRepository.save(registration);
        }

        donationProcessRepository.save(process);
    }
}
