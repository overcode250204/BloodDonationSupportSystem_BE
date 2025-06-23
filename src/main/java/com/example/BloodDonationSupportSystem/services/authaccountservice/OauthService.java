package com.example.BloodDonationSupportSystem.services.authaccountservice;
import com.example.BloodDonationSupportSystem.entities.OauthAccountEntity;
import com.example.BloodDonationSupportSystem.repositories.OauthAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OauthService {

    @Autowired
    private OauthAccountRepository oauthAccountRepository;

    public Optional<OauthAccountEntity> getOauthAccount(String provider, String providerUserId) {
        return oauthAccountRepository.findByProviderAndProviderUserId(provider, providerUserId);
    }

    public OauthAccountEntity saveOauthAccount(OauthAccountEntity oauthAccount) {
        return oauthAccountRepository.save(oauthAccount);
    }
}