package com.example.BloodDonationSupportSystem.service.userservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    public UserProfileDTO getUserProfile() {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID userId;
            try {
                userId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for user ID: " + currentUser.getUsername());
            }
            UserEntity userEntity = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("UserId Not Found At getUserProfile()" + currentUser.getUsername()));
            return convertToResponse(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting current user profile");
        }
    }

    public UserProfileDTO updateUserProfile(@Valid UserProfileDTO user) {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID userId;
            try {
                userId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for user ID: " + currentUser.getUsername());
            }
            UserEntity userEntity = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("UserId Not Found At updateUserProfile()" + currentUser.getUsername()));
            userEntity.setFullName(user.getFullName());
            userEntity.setAddress(user.getAddress());
            userEntity.setDateOfBirth(user.getDayOfBirth());
            userEntity.setGender(user.getGender());
            return convertToResponse(userRepository.save(userEntity));
        } catch (Exception e) {
            throw new RuntimeException("Error while updating current user profile");
        }
    }

    private UserProfileDTO convertToResponse(UserEntity user) {
        return UserProfileDTO.builder()
                .id(user.getUserId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dayOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .bloodType(user.getBloodType())
                .build();
    }
}
