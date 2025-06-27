package com.example.BloodDonationSupportSystem.service.searchdistanceservice;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonorResponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.GeoLocation;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchDistanceService {
    @Value("${GOONG_API_KEY}")
    private String goongApiKey;

    //Vị trí tìm kiếm bắt đầu từ Đại học FPT HCM
    private static final double FPT_HCM_LATITUDE = 10.841416800000001;
    private static final double FPT_HCM_LONGTITUDE = 106.81007447258705;
    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    public List<DonorResponse> getEligibleDonors(List<String> bloodTypes, double maxDistanceKm) {

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Object[]> rawResults = userRepository.findEligibleDonors(
                bloodTypes, maxDistanceKm, FPT_HCM_LATITUDE, FPT_HCM_LONGTITUDE, threeMonthsAgo);


        return rawResults.stream()
                .map(obj -> new DonorResponse(
                        (String) obj[0],
                        (String) obj[1],
                        obj[2] instanceof Date
                                ? (Date) obj[2]
                                : java.sql.Date.valueOf(obj[2].toString()),
                        (String) obj[3]
                ))
                .collect(Collectors.toList());
    }

    // Hàm này sẽ lấy tọa độ của địa chỉ xử lí cho trước khi lưu vào DB
    public GeoLocation getCoordinates(String address) {
        double lat = 0.0;
        double lon = 0.0;

        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://rsapi.goong.io/Geocode?address=" + encodedAddress + "&api_key=" + goongApiKey;

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject json = new JSONObject(response.toString());
                JSONArray results = json.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0)
                            .getJSONObject("geometry")
                            .getJSONObject("location");
                    lat = location.getDouble("lat");
                    lon = location.getDouble("lng");

                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching coordinates from Goong: " + e.getMessage());
        }

        return new GeoLocation(lat, lon);
    }

}
