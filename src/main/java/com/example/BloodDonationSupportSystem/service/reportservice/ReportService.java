package com.example.BloodDonationSupportSystem.service.reportservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;
import com.example.BloodDonationSupportSystem.dto.reportDTO.BloodDonationReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.OverviewReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequest;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequestByDate;
import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import com.example.BloodDonationSupportSystem.repository.BloodDonationScheduleRepository;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    BloodDonationScheduleRepository bloodDonationScheduleRepository;



    public void exportBloodInventoryReportToExcel( HttpServletResponse response) throws IOException {
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

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=donation_report.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportBloodDonationReportToExcel(ReportFilterRequestByDate request ,HttpServletResponse response) throws IOException {
        List<Object[]> reportData = bloodDonationScheduleRepository.getDonationReport(Date.valueOf(request.getStartDate()),
                Date.valueOf(request.getEndDate()));

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo Cáo Hiến Máu");


        Row headerRow = sheet.createRow(0);
        String[] headers = {"Ngày hiến", "Bệnh viện", "Tổng đăng ký", "Đã hiến", "Hủy", "Tổng lượng máu (ml)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }


        int rowNum = 1;
        for (Object[] row : reportData) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(row[0].toString());
            dataRow.createCell(1).setCellValue(row[1].toString());
            dataRow.createCell(2).setCellValue(((Number) row[2]).intValue());
            dataRow.createCell(3).setCellValue(((Number) row[3]).intValue());
            dataRow.createCell(4).setCellValue(((Number) row[4]).intValue());
            dataRow.createCell(5).setCellValue(((Number) row[5]).doubleValue());
        }


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=donation_report.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
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

    public Map<Integer, Map<String, Integer>> getMonthlyStats(ReportFilterRequest request) {
        List<Object[]> results = bloodDonationRegistrationRepository.getMonthlyDonationStats(request.getYear());

        Map<Integer, Map<String, Integer>> monthlyData = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            Map<String, Integer> counts = new HashMap<>();
            counts.put("successCount", 0);
            counts.put("failedCount", 0);
            monthlyData.put(i, counts);
        }


        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            Integer successCount = ((Number) row[1]).intValue();
            Integer failedCount = ((Number) row[2]).intValue();

            Map<String, Integer> counts = new HashMap<>();
            counts.put("successCount", successCount);
            counts.put("failedCount", failedCount);
            monthlyData.put(month, counts);
        }

        return monthlyData;
    }

    public Map<String, Object> getBloodVolume(ReportFilterRequest request) {

        LocalDate endDate = LocalDate.of(request.getYear(), request.getMonth(), 1).withDayOfMonth(
                LocalDate.of(request.getYear(), request.getMonth(), 1).lengthOfMonth()
        );

        List<Object[]> results = bloodInventoryRepository.getBloodVolume(Date.valueOf(endDate));


        Map<String, Integer> bloodVolumeData = new LinkedHashMap<>();
        for (Object[] row : results) {
            String bloodTypeId = row[0].toString();
            Integer totalVolume = ((Number) row[1]).intValue();
            bloodVolumeData.put(bloodTypeId, totalVolume);
        }

        return  Map.of("bloodVolumeData", bloodVolumeData);
    }

    public  List<BloodDonationReportDTO> getDonationReport(ReportFilterRequestByDate request) {
        List<Object[]> result = bloodDonationScheduleRepository.getDonationReport(
                Date.valueOf(request.getStartDate()),
                Date.valueOf(request.getEndDate())
        );

        List<BloodDonationReportDTO>  bloodDonationReportDTOList = new ArrayList<>();

        for (Object[] row : result) {
            String date =  row[0].toString();
            String hospital = (String) row[1];
            int totalRegistration = ((Number) row[2]).intValue();
            int totalSuccess = ((Number) row[3]).intValue();
            int totalFailed = ((Number) row[4]).intValue();
            int totalVolume = ((Number) row[5]).intValue();
            BloodDonationReportDTO bloodDonationReportDTO = new BloodDonationReportDTO(date, hospital, totalRegistration, totalSuccess, totalFailed, totalVolume);

            bloodDonationReportDTOList.add(bloodDonationReportDTO);
        }

        return  bloodDonationReportDTOList;
    }

    public List<BloodInventoryResponse> getBloodInventory() {
        List<BloodInventory> bloodTotalList = bloodInventoryRepository.findAll();

        return  bloodTotalList.stream()
                .map(allBloodBagInventory -> new BloodInventoryResponse(
                        allBloodBagInventory.getBloodTypeId(),
                        allBloodBagInventory.getTotalVolumeMl())).toList();
    }

}
