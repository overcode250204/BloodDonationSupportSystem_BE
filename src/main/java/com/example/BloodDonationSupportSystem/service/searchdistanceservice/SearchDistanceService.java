package com.example.BloodDonationSupportSystem.service.searchdistanceservice;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonorResponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.GeoLocation;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchDistanceService {
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
        String lat= "0.0";
        String lon = "0.0";
        try {
            String query = address;
            String url = "https://nominatim.openstreetmap.org/search?q=" +
                    query.replace(" ", "+") + "&format=json";
            String response = restTemplate.getForObject(url, String.class);
            JSONArray arr = new JSONArray(response);
            if (arr.length() > 0) {
                //  nó tìm ra nhìu kết quả thì no sẽ lấy kết quả đầu tiên
                JSONObject obj = arr.getJSONObject(0);
                lat = obj.getString("lat");
                lon = obj.getString("lon");

            }
            return new GeoLocation(Double.parseDouble(lat), Double.parseDouble(lon));

        } catch (Exception e) {
            System.out.println("Error in geocoding: " + e.getMessage());
            return new GeoLocation(0.0, 0.0);
        }
    }

}
