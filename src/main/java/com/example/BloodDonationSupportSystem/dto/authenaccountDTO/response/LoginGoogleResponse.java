package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginGoogleResponse {
   private String access_token;
   private String user_id;
   private String user_name;
   private String avatar_url;
   private Enum role;
}
