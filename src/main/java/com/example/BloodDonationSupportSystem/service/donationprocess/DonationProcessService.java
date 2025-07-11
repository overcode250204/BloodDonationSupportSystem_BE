package com.example.BloodDonationSupportSystem.service.donationprocess;

import com.example.BloodDonationSupportSystem.dto.donationprocessDTO.DonationProcessDTO;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.entity.OauthAccountEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.DonationProcessRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.OauthAccountRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.service.emailservice.EmailService;
import com.example.BloodDonationSupportSystem.service.smsservice.SmsService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DonationProcessService {

    @Autowired
    private DonationProcessRepository donationProcessRepository;

    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OauthAccountRepository oauthAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DonationProcessDTO> getDonationProcessByStaffId(UUID staffId){
        List<Object[]> donationProcesses = donationProcessRepository.findDonationProcessByStaffId(staffId);

        return donationProcesses.stream().map(row -> new DonationProcessDTO(
                UUID.fromString(row[0].toString()), // donation_process_id
                row[1].toString(),                  // donor full name
                LocalDate.parse(row[2].toString()),                 // registration_date
                row[3] != null ? row[3].toString() : null, // urgency
                row[4].toString(),                  // status
                row[5].toString(),                  // process_status
                row[6] != null ? row[6].toString() : null,   // note
                row[7] != null ? ((Number) row[7]).intValue() : 0, //volumeMl
                UUID.fromString(row[8].toString()), // donation_registration_id
                UUID.fromString(row[9].toString())  // screened_by_staff_id
        )).toList();
    }

    @Transactional
    public void updateDonationProcess(DonationProcessDTO request) throws MessagingException {
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
            if(registration.getDonor().getPhoneNumber() != null){
                smsService.sendSmsSuccessRegistrationNotification(registration.getDonor().getPhoneNumber(), registration.getDateCompleteDonation().toString());
            } else {
                Optional<UserEntity> user = userRepository.findByUserId(registration.getDonor().getUserId());
                if (user.isPresent()) {
                    UserEntity u = user.get();
                    OauthAccountEntity email = oauthAccountRepository.findByUser(u);
                    emailService.sendSuccessRegistrationNotification(registration.getDonor().getFullName(), email.getAccount(), registration.getDateCompleteDonation().toString(), registration.getBloodDonationSchedule().getAddressHospital());
                } else {
                   throw new ResourceNotFoundException("User not found");
                }

            }
        }

        donationProcessRepository.save(process);
    }
}
