package com.example.BloodDonationSupportSystem.service.userservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.service.searchdistanceservice.SearchDistanceService;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchDistanceService searchDistanceService;

    public UserProfileDTO getCurrentUserProfile() {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID userId;
            try {
                userId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for user ID: " + currentUser.getUsername());
            }
            UserEntity userEntity = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("UserId Not Found At getCurrentUserProfile()" + currentUser.getUsername()));
            UserProfileDTO userProfileDTO = getUserProfileDTO(userEntity);
            return userProfileDTO;
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
            if (user.getAddress() != null) {
                searchDistanceService.updateCoordinate();
            }
            userEntity.setDateOfBirth(user.getDayOfBirth());
            userEntity.setGender(user.getGender());
            return getUserProfileDTO(userRepository.save(userEntity));
        } catch (Exception e) {
            throw new RuntimeException("Error while updating current user profile");
        }
    }

    private UserProfileDTO getUserProfileDTO(UserEntity userEntity) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(userEntity.getUserId());
        userProfileDTO.setFullName(userEntity.getFullName());
        userProfileDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userProfileDTO.setGender(userEntity.getGender());
        userProfileDTO.setDayOfBirth(userEntity.getDateOfBirth());
        userProfileDTO.setAddress(userEntity.getAddress());
        userProfileDTO.setBloodType(userEntity.getBloodType());
        userProfileDTO.setLongitude(userEntity.getLongitude());
        userProfileDTO.setLatitude(userEntity.getLatitude());
        userProfileDTO.setRole(userEntity.getRole().getRoleName());
        if (userEntity.getOauthAccount() != null) {
            userProfileDTO.setEmail(userEntity.getOauthAccount().getAccount());
        } else {
            userProfileDTO.setEmail(null);
        }
        return userProfileDTO;
    }




}
