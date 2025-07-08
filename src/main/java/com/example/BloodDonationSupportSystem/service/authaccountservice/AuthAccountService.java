package com.example.BloodDonationSupportSystem.service.authaccountservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.LoginRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.RegisterRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.LoginAccountResponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.RegisterAccountReponse;
import com.example.BloodDonationSupportSystem.entity.RoleEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.RoleRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.service.jwtservice.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.GeoLocation;
import com.example.BloodDonationSupportSystem.service.searchdistanceservice.SearchDistanceService;
@Service
public class AuthAccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SearchDistanceService searchDistanceService;
    public RegisterAccountReponse register(RegisterRequest registerRequest) {
        RegisterAccountReponse response;
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new BadRequestException("Phone number already in use");
        }
        GeoLocation location = searchDistanceService.getCoordinates(registerRequest.getAddress());
        UserEntity user = new UserEntity();
            user.setPhoneNumber(registerRequest.getPhoneNumber());
            Optional<RoleEntity> roleMember = roleRepository.findByRoleName("ROLE_MEMBER");
            user.setRole(roleMember.orElseThrow(() -> new ResourceNotFoundException("Cannot find role")));
            user.setFullName(registerRequest.getFullName());
            user.setAddress(registerRequest.getAddress());
            user.setLongitude(location.getLongitude());
            user.setLatitude(location.getLatitude());
            user.setDateOfBirth(registerRequest.getDateOfBirth());
            user.setGender(registerRequest.getGender());
            user.setStatus(registerRequest.getStatus());
            user.setPasswordHash(passwordEncoder.encode(registerRequest.getConfirmPassword()));
            userRepository.save(user);
            response = new RegisterAccountReponse();
            response.setMessage("Registration successful");





        return response;
    }

    public LoginAccountResponse authAccount(LoginRequest loginRequest) {

        LoginAccountResponse loginAccountResponse;
        UserEntity user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber()).orElseThrow(() -> new BadRequestException("PhoneNumber doesn't exist"));
        if (!user.getStatus().equals("HOẠT ĐỘNG")) {
            throw new BadRequestException("Account is locked or inactive!");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Incorrect username or password!!!");
        }

        String token = jwtService.generateToken(new User(user.getUserId().toString(), user.getPasswordHash(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName()))));
        loginAccountResponse = new LoginAccountResponse(token);
        return loginAccountResponse;


    }






}
