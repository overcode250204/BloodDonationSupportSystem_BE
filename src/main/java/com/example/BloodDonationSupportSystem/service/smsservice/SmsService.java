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

    public String sendSmsHealthReminder(String phoneNumber, String name) {
        try {
            String message = "Xin chào " + name + ",\n\n" +
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

    public String sendSmsSuccessRegistrationNotification(String phoneNumber, String name, String dateTime, String location) {

        try {
            String message = "Xin chào " + name + ",\n\n" +
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

    public String sendRegistrationFailureNotification(String phoneNumber) {
        try {
            String message = "Chúng tôi rất tiếc phải thông báo rằng đơn đăng ký hiến máu của bạn không thành công " +
                    "vì hiện tại không có lịch hiến máu nào phù hợp với khoảng thời gian mà bạn đã chọn.\n\n" +
                    "Bạn có thể truy cập hệ thống để đăng ký lại với một khoảng thời gian khác hoặc chờ đợi khi có lịch mới được cập nhật.\n\n" +
                    "Cảm ơn bạn đã quan tâm và sẵn sàng đóng góp để giúp đỡ những người cần máu. " +
                    "Chúng tôi hy vọng sẽ sớm đồng hành cùng bạn trong các chương trình hiến máu tiếp theo.\n\n" +
                    "Nếu có bất kỳ thắc mắc hoặc cần hỗ trợ, vui lòng liên hệ:\n" +

                    "Số điện thoại: " + phoneNumber + "\n\n" +
                    "Trân trọng,\n" +
                    "Trung tâm Hiến máu";
            return speedSMSUtils.sendSMS(phoneNumber, message, 2, device);
        } catch (IOException e) {
            e.printStackTrace();
            return "Send SMS fail" + e.getMessage();
        }
    }



}