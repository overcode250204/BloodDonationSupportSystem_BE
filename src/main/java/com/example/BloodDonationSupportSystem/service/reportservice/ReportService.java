package com.example.BloodDonationSupportSystem.service.reportservice;

import com.example.BloodDonationSupportSystem.dto.reportDTO.DonationRegistrationReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.OverviewReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequest;
import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    DonationRegistrationRepository donationRegistrationRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    DonationRegistrationRepository bloodDonationRegistrationRepository;

//    public void exportDonationReportToExcel(ReportFilterRequest filter, OutputStream out) throws IOException {
//        List<DonationRegistrationReportDTO> data =
//                donationRegistrationRepository.getDonationReport(filter.getFromDate(), filter.getToDate());
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Báo cáo ĐK hiến máu");
//
//        String[] headers = {"STT", "Họ tên", "SĐT", "Nhóm máu", "Ngày ĐK", "Ngày hoàn thành", "Trạng thái", "Địa chỉ", "Nhân viên xử lý", "Bệnh viện"};
//
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < headers.length; i++) {
//            headerRow.createCell(i).setCellValue(headers[i]);
//        }
//
//        int rowNum = 1;
//        int index = 1;
//        for (DonationRegistrationReportDTO dto : data) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(index++);
//            row.createCell(1).setCellValue(dto.getFullName());
//            row.createCell(2).setCellValue(dto.getPhoneNumber());
//            row.createCell(3).setCellValue(dto.getBloodType());
//            row.createCell(4).setCellValue(dto.getRegistrationDate().toString());
//            row.createCell(5).setCellValue(dto.getDateCompleteDonation() != null ? dto.getDateCompleteDonation().toString() : "");
//            row.createCell(6).setCellValue(dto.getStatus());
//            row.createCell(7).setCellValue(dto.getAddress());
//            row.createCell(8).setCellValue(dto.getStaffName() != null ? dto.getStaffName() : "");
//            row.createCell(9).setCellValue(dto.getHospitalAddress() != null ? dto.getHospitalAddress() : "");
//        }
//
//        workbook.write(out);
//        workbook.close();
//    }

    public void exportBloodInventoryReportToExcel( OutputStream out) throws IOException {
        List<BloodInventory> data = bloodInventoryRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo kho máu");

        String[] headers = {"STT", "Nhóm Máu", "Số lượng"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rowNum = 1;
        int index = 1;
        for (BloodInventory bi : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(index++);
            row.createCell(1).setCellValue(bi.getBloodTypeId());
            row.createCell(2).setCellValue(bi.getTotalVolumeMl());
            System.out.println(bi.getBloodTypeId());
        }

        workbook.write(out);
        workbook.close();
    }

    public OverviewReportDTO getOverview(ReportFilterRequest request){
        OverviewReportDTO overviewReportDTO = new OverviewReportDTO();
        overviewReportDTO.setNumberAccount(userRepository.countFilter(request.getYear(), request.getMonth()));
        overviewReportDTO.setNumberBloodDonationsRegistration(bloodDonationRegistrationRepository.countFilter(request.getYear(), request.getMonth()));
        overviewReportDTO.setNumberSuccessDonation(bloodDonationRegistrationRepository.countNumberSuccessDonationFilter(request.getYear(), request.getMonth()));
        overviewReportDTO.setNumberFailureDonation(bloodDonationRegistrationRepository.countNumberFailureDonationFilter(request.getYear(), request.getMonth()));
        overviewReportDTO.setNumberNotCompleteDonation(bloodDonationRegistrationRepository.countNumberNotCompleteDonationFilter(request.getYear(), request.getMonth()));
        overviewReportDTO.setNumberNotAcceptedDonation(bloodDonationRegistrationRepository.countNumberNotAcceptedDonationFilter(request.getYear(), request.getMonth()));
        return overviewReportDTO;
    }



}
