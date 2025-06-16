package com.example.BloodDonationSupportSystem.service.userservice;

import com.example.BloodDonationSupportSystem.Utils.AuthUtils;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.UpdateUserProfileRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.UserProfileResponse;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    public UserProfileResponse getUserProfile() {
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
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setFullName(userEntity.getFullName());
            userProfileResponse.setPhoneNumber(userEntity.getPhoneNumber());
            userProfileResponse.setGender(userEntity.getGender());
            userProfileResponse.setDayOfBirth(userEntity.getDateOfBirth());
            userProfileResponse.setAddress(userEntity.getAddress());
            userProfileResponse.setBloodType(userEntity.getBloodType());
            return userProfileResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting current user profile");
        }
    }

    public UserProfileResponse updateUserProfile(@Valid UpdateUserProfileRequest user) {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID userId;
            try {
                userId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for user ID: " + currentUser.getUsername());
            }
            UserEntity userEntity = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("UserId Not Found At updateUserProfile()" + currentUser.getUsername()));
            userEntity.setFullName(user.getFullName());
            userEntity.setAddress(user.getAddress());
            userEntity.setDateOfBirth(user.getDateOfBirth());
            userEntity.setGender(user.getGender());
            userEntity = userRepository.save(userEntity);
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setFullName(userEntity.getFullName());
            userProfileResponse.setPhoneNumber(userEntity.getPhoneNumber());
            userProfileResponse.setGender(userEntity.getGender());
            userProfileResponse.setDayOfBirth(userEntity.getDateOfBirth());
            userProfileResponse.setAddress(userEntity.getAddress());
            userProfileResponse.setBloodType(userEntity.getBloodType());
            return userProfileResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error while updating current user profile");
        }
    }
}
