package com.example.BloodDonationSupportSystem.service.smsservice;



import com.example.BloodDonationSupportSystem.utils.SpeedSMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SmsService {
    @Autowired
    private SpeedSMSUtils speedSMSUtils;

    @Value("${device}")
    private String device;

    public String sendSmsHealthReminder(String phoneNumber, String username) {
        try {
            String message = "Xin chào " + username + ",\n\n" +
                    "Cảm ơn bạn đã tham gia hiến máu tình nguyện.\n\n" +
                    "Vui lòng theo dõi tình trạng sức khỏe trong 48 giờ tới. " +
                    "Nếu có dấu hiệu bất thường như chóng mặt, mệt mỏi kéo dài, hãy đến trung tâm y tế gần nhất.\n\n" +
                    "Trân trọng,\n" +
                    "Trung tâm Hiến máu";



            return speedSMSUtils.sendSMS(phoneNumber, message, 2, device);
        } catch (IOException e) {
            e.printStackTrace();
            return "Send SMS fail" + e.getMessage();
        }

    }

    public String sendSmsSuccessRegistrationNotification(String phoneNumber, String username, String dateTime, String location) {

        try {
            String message = "Xin chào " + username + ",\n\n" +
                    "Chúc mừng bạn đã đăng ký hiến máu thành công!\n\n" +
                    "Thời gian hiến máu: " + dateTime + "\n" +
                    "Địa điểm hiến máu: " + location + "\n\n" +
                    "Cảm ơn bạn đã dành thời gian và tấm lòng để giúp đỡ những người cần máu. " +
                    "Mỗi đơn vị máu bạn hiến tặng sẽ góp phần cứu sống nhiều người bệnh và mang lại hy vọng cho họ.\n\n" +
                    "Lưu ý quan trọng trước khi hiến máu:\n" +
                    "- Ăn nhẹ và tránh sử dụng đồ uống có cồn trước khi hiến máu.\n" +
                    "- Nghỉ ngơi đầy đủ và mang theo giấy tờ tùy thân khi đến địa điểm hiến máu.\n\n" +
                    "Nếu có bất kỳ thắc mắc hoặc cần thay đổi thông tin đăng ký, vui lòng liên hệ:\n" +
                    "Số điện thoại: " + phoneNumber + "\n\n" +
                    "Một lần nữa xin cảm ơn tấm lòng nhân ái của bạn.\n\n" +
                    "Trân trọng,\n" +
                    "Trung tâm Hiến máu";
            return speedSMSUtils.sendSMS(phoneNumber, message, 2, device);
        } catch (IOException e) {
            e.printStackTrace();
            return "Send SMS fail" + e.getMessage();
        }


    }




}