package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.request.ReportReq;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportComponentImpl implements ReportComponent {
    private final Workbook workbook;
    private Sheet sheet;
    private final ReportMapper reportMapper;
    private static final int TITLE_ROW = 0;
    private static final int HEADER_ROW = 1;

    public ReportComponentImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
        workbook = new HSSFWorkbook();
    }

    @Override
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        export(response, reportReq);
    }

    private void export(HttpServletResponse response, ReportReq reportReq) throws IOException {
        writeTitleLine(reportReq);
        writeHeaderLine(reportReq);
        writeDataLines(reportReq);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeTitleLine(ReportReq reportReq) {
        sheet = workbook.createSheet(reportReq.getReportType().toString());
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 400);
        style.setFont(font);
        Row row = sheet.createRow(TITLE_ROW);
        sheet.addMergedRegion(new CellRangeAddress(TITLE_ROW, TITLE_ROW, 0, 4));
        createCell(row, 0, reportReq.getReportType().toString(), style);
    }

    private void writeHeaderLine(ReportReq reportReq) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 320);
        style.setFont(font);
        if (reportReq.getReportType().equals(ReportType.SCHEDULE)) {
            writeScheduleHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLES)) {
            writeVehiclesHeaderLine(style);
        }
    }

    private void writeScheduleHeaderLine(CellStyle style) {
        Row row = sheet.createRow(HEADER_ROW);
        createCell(row, 0, "No", style);
        createCell(row, 1, "Contract Id", style);
        createCell(row, 2, "Date", style);
        createCell(row, 3, "Departure Location", style);
        createCell(row, 4, "Destination Location", style);
        createCell(row, 5, "Contract Value", style);
        createCell(row, 6, "Contract Status", style);
        createCell(row, 7, "Vehicle Id", style);
        createCell(row, 8, "Driver Id", style);
        createCell(row, 9, "Driver Name", style);
        createCell(row, 10, "Contributor Id", style);
    }

    private void writeVehiclesHeaderLine(CellStyle style) {

        int totalVehicle = reportMapper.getTotalVehicleForReport();

        Row totalVehicleRow = sheet.createRow(HEADER_ROW);
        createCell(totalVehicleRow, 1, "Total Vehicles: ", style);
        createCell(totalVehicleRow, 2, totalVehicle, style);

        Row row = sheet.createRow(HEADER_ROW + 1);
        createCell(row, 0, "No", style);
        createCell(row, 1, "Vehicle Id", style);
        createCell(row, 2, "Type", style);
        createCell(row, 3, "Brand", style);
        createCell(row, 4, "Owner Id", style);
        createCell(row, 5, "Owner Name", style);
    }

    private void writeDataLines(ReportReq reportReq) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 280);
        style.setFont(font);
        if (reportReq.getReportType().equals(ReportType.SCHEDULE)) {
            writeScheduleDataLines(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLES)) {
            writeVehicleDataLines(style);
        }
    }

    private void writeScheduleDataLines(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 1;
        int numberOfData = 1;
        List<LocalDate> firstAndLastDaysInMonth = getFirstAndLastDayInAMonth(reportReq);
        List<Schedule> schedules =
                reportMapper.getListSchedule(
                        firstAndLastDaysInMonth.get(0).toString(),
                        firstAndLastDaysInMonth.get(1).toString());

        for (Schedule schedule : schedules) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            int detailSize = schedule.getDetails().size();
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, schedule.getContractId(), style);
            createCell(row, columnCount++, schedule.getDate().toString(), style);
            createCell(row, columnCount++, schedule.getDepartureLocation(), style);
            createCell(row, columnCount++, schedule.getDestinationLocation(), style);
            createCell(row, columnCount++, schedule.getContractValue(), style);
            createCell(row, columnCount++, schedule.getContractStatus().toString(), style);
            for (ScheduleDetail scheduleDetail : schedule.getDetails()) {
                createCell(row, columnCount, scheduleDetail.getVehicleId(), style);
                createCell(row, columnCount + 1, scheduleDetail.getDriverId(), style);
                createCell(row, columnCount + 2, scheduleDetail.getDriverName(), style);
                createCell(row, columnCount + 3, scheduleDetail.getContributorId(), style);
                if (--detailSize > 0) {
                    row = sheet.createRow(rowCount++);
                }
            }
        }
        Row row = sheet.createRow(++rowCount);
        createCell(row, 4, "Total Value", style);
        Cell valueCell = row.createCell(5);
        valueCell.setCellFormula("SUM(F3:F" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }

    private void writeVehicleDataLines(CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<VehicleReport> vehicleReports = reportMapper.getVehiclesForReport();
        for (VehicleReport vehicleReport : vehicleReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, vehicleReport.getVehicleId(), style);
            createCell(row, columnCount++, vehicleReport.getVehicleType(), style);
            createCell(row, columnCount++, vehicleReport.getBrand(), style);
            createCell(row, columnCount++, vehicleReport.getOwnerId(), style);
            createCell(row, columnCount, vehicleReport.getOwnerName(), style);
        }


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private List<LocalDate> getFirstAndLastDayInAMonth(ReportReq reportReq) {
        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? Integer.parseInt(reportReq.getYear()) : currentDate.getYear();
        int month = reportReq.getMonth() != null ? Integer.parseInt(reportReq.getMonth()) : currentDate.getMonthValue();
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        List<LocalDate> firstAndLast = new ArrayList<>();
        firstAndLast.add(firstDayOfMonth);
        firstAndLast.add(lastDayOfMonth);
        return firstAndLast;
    }
}