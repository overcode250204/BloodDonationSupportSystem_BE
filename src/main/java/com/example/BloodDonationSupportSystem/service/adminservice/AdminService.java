package com.example.BloodDonationSupportSystem.service.adminservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.UpdateUserStatusRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.UserAccountResponse;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<UserAccountResponse> getAllUser() {
        try {
            List<UserEntity> users = userRepository.findAll();
            return users.stream()
                    .map(this::convertToRespone)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all users account");
        }
    }

    public UserAccountResponse updateStatusUser(UUID userID, @Valid UpdateUserStatusRequest request) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByUserId(userID);
            if (optionalUser.isEmpty()) {
                throw new RuntimeException("User not found with ID:" + userID);
            }
            UserEntity user = optionalUser.get();
            user.setStatus(request.getStatus());
            return convertToRespone(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user account status");
        }
    }

    private UserAccountResponse convertToRespone(UserEntity user) {
        return UserAccountResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .dayOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .bloodType(user.getBloodType())
                .status(user.getStatus())
                .roleName(user.getRole().getRoleName())
                .build();
    }
}
