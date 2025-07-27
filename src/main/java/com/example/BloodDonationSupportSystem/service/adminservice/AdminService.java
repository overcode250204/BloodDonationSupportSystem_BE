package com.example.BloodDonationSupportSystem.service.adminservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.entity.RoleEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.RoleRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

            List<UserEntity> filteredUsers = users.stream()
                    .filter(user -> user.getRole() != null)
                    .filter(user -> {
                        String roleName = user.getRole().getRoleName().toUpperCase();
                        return roleName.equals("ROLE_MEMBER") || roleName.equals("ROLE_STAFF");
                    })
                    .collect(Collectors.toList());

            if (filteredUsers.isEmpty()) {
                throw new ResourceNotFoundException("No users found with role MEMBER or STAFF");
            }

            return filteredUsers.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while getting users with role MEMBER or STAFF");
        }
    }

    public UserProfileDTO updateUser(UUID userID, @Valid UserProfileDTO request) {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID currentUserId;

            try {
                currentUserId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for current user ID: " + currentUser.getUsername());
            }

            UserEntity currentUserEntity = userRepository.findByUserId(currentUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

            if (currentUserEntity.getRole() == null ||
                    !currentUserEntity.getRole().getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
                throw new BadRequestException("Only ADMIN can update user status or role");
            }

            Optional<UserEntity> optionalUser = userRepository.findByUserId(userID);

            if (optionalUser.isEmpty()) {
                throw new ResourceNotFoundException("User not found with ID:" + userID);
            }

            UserEntity user = optionalUser.get();
            if (request.getStatus() != null) {
                user.setStatus(request.getStatus());
            }
            if (request.getRole() != null) {
                String newRoleName = request.getRole().toUpperCase();

                if (!newRoleName.equals("ROLE_MEMBER") && !newRoleName.equals("ROLE_STAFF")) {
                    throw new BadRequestException("Can only assign ROLE_MEMBER or ROLE_STAFF");
                }

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
