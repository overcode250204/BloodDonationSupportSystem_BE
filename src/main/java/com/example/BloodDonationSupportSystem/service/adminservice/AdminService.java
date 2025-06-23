package com.example.BloodDonationSupportSystem.service.adminservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.entity.RoleEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    public List<UserProfileDTO> getAllUsers() {
        try {
            List<UserEntity> users = userRepository.findAll();
            if(users.isEmpty()) {
                throw new ResourceNotFoundException("No users found");
            }
            return users.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all users account");
        }
    }

    public UserProfileDTO updateUser(UUID userID, @Valid UserProfileDTO request) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByUserId(userID);
            if (optionalUser.isEmpty()) {
                throw new ResourceNotFoundException("User not found with ID:" + userID);
            }
            UserEntity user = optionalUser.get();
            if (request.getStatus() != null) {
                user.setStatus(request.getStatus());
            }
            if (request.getRole() != null) {
                RoleEntity role = roleRepository.findByRoleName(request.getRole())
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + request.getRole()));
                user.setRole(role);
            }
            return convertToResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user account status");
        }
    }

    private UserProfileDTO convertToResponse(UserEntity user) {
        return UserProfileDTO.builder()
                .id(user.getUserId())
                .fullName(user.getFullName())
                .dayOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .bloodType(user.getBloodType())
                .status(user.getStatus())
                .role(user.getRole().getRoleName())
                .build();
    }
}
