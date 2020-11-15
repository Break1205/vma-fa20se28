package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.model.VehicleRevenueExpense;
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
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportComponentImpl implements ReportComponent {
    private Workbook workbook;
    private Sheet sheet;
    private List<LocalDate> firstAndLast;
    private final ReportMapper reportMapper;
    private static final int TITLE_ROW = 0;
    private static final int HEADER_ROW = 1;

    public ReportComponentImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    @Override
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        export(response, reportReq);
    }

    private void export(HttpServletResponse response, ReportReq reportReq) throws IOException {
        workbook = new HSSFWorkbook();
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
        if (reportReq.getReportType().equals(ReportType.VEHICLES)) {
            writeVehiclesHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.SCHEDULE)) {
            firstAndLast = getFirstAndLastDayInAMonth(reportReq);
            writeScheduleHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLE_REVENUE_EXPENSE)) {
            firstAndLast = getFirstAndLastDayInAMonth(reportReq);
            writeVehicleRevenueExpenseHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE)) {
            firstAndLast = getFirstAndLastDayInAMonth(reportReq);
            writeMaintenanceHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRACTS)) {
            firstAndLast = getFirstAndLastDayInAMonth(reportReq);
            writeContractsHeaderLine(style);
        }
    }

    private void writeDataLines(ReportReq reportReq) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 280);
        style.setFont(font);
        if (reportReq.getReportType().equals(ReportType.VEHICLES)) {
            writeVehicleDataLines(style);
        } else if (reportReq.getReportType().equals(ReportType.SCHEDULE)) {
            writeScheduleDataLines(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLE_REVENUE_EXPENSE)) {
            writeVehicleRevenueExpenseDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE)) {
            writeMaintenanceDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRACTS)) {
            writeContractsDataLine(style);
        }
    }

    // Schedule
    private void writeScheduleHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

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

    private void writeScheduleDataLines(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;
        List<Schedule> schedules =
                reportMapper.getListSchedule(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString());

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

    // end Schedule


    // Vehicle
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
    // end Vehicles


    // Vehicle Revenue Expense
    private void writeVehicleRevenueExpenseHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Type", style);
        createCell(row, 3, "Value", style);
        createCell(row, 4, "Contract Id", style);
        createCell(row, 5, "Customer Id", style);
    }

    private void writeVehicleRevenueExpenseDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<VehicleRevenueExpense> vehicleRevenueExpenses =
                reportMapper.getVehicleRevenueExpenseForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId());
        for (VehicleRevenueExpense vehicleRevenueExpense : vehicleRevenueExpenses) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, vehicleRevenueExpense.getDate().toString(), style);
            createCell(row, columnCount++, vehicleRevenueExpense.getType(), style);
            createCell(row, columnCount++, vehicleRevenueExpense.getValue(), style);
            createCell(row, columnCount++, vehicleRevenueExpense.getContractId(), style);
            createCell(row, columnCount, vehicleRevenueExpense.getCustomerId(), style);
        }
    }

    // end Vehicle Revenue Expense

    // Maintenance

    private void writeMaintenanceHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);
        createCell(row, 0, "No", style);
        createCell(row, 1, "Maintenance Date", style);
        createCell(row, 2, "Maintenance Type", style);
        createCell(row, 3, "Cost", style);
    }

    private void writeMaintenanceDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<MaintenanceReport> maintenanceReports =
                reportMapper.getMaintenanceByVehicleIdForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId());

        for (MaintenanceReport maintenanceReport : maintenanceReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, maintenanceReport.getMaintenanceDate().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getMaintenanceType().toString(), style);
            createCell(row, columnCount, maintenanceReport.getCost(), style);
        }
    }

    // end Maintenance

    // Contracts

    private void writeContractsHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Contract Id", style);
        createCell(row, 2, "Contract Value", style);
        createCell(row, 3, "Departure Location", style);
        createCell(row, 4, "Destination Location", style);
        createCell(row, 5, "Destination Time", style);
        createCell(row, 6, "Customer Id", style);
        createCell(row, 7, "Customer Name", style);
        createCell(row, 8, "Phone Number", style);
        createCell(row, 9, "Email", style);
        createCell(row, 10, "Fax", style);
        createCell(row, 11, "Account Number", style);
        createCell(row, 12, "Tax Code", style);
    }

    private void writeContractsDataLine(CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<ContractReport> contractReports =
                reportMapper.getContractsReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString());

        for (ContractReport contractReport : contractReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, contractReport.getContractId(), style);
            createCell(row, columnCount++, contractReport.getContractValue(), style);
            createCell(row, columnCount++, contractReport.getDepartureLocation(), style);
            createCell(row, columnCount++, contractReport.getDestinationLocation(), style);
            createCell(row, columnCount++, contractReport.getDestinationTime().toString(), style);
            createCell(row, columnCount++, contractReport.getCustomerId(), style);
            createCell(row, columnCount++, contractReport.getCustomerName(), style);
            createCell(row, columnCount++, contractReport.getPhoneNumber(), style);
            createCell(row, columnCount++, contractReport.getEmail(), style);
            createCell(row, columnCount++, contractReport.getFax(), style);
            createCell(row, columnCount++, contractReport.getAccountNumber(), style);
            createCell(row, columnCount, contractReport.getTaxCode(), style);
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
        int year = reportReq.getYear() != null ? reportReq.getYear() : currentDate.getYear();
        Month firstMonthOfQuarter = currentDate.getMonth();
        Month lastMonthOfQuarter = currentDate.getMonth();
        if (reportReq.getQuarter() == null) {
            firstMonthOfQuarter = Month.JANUARY;
            lastMonthOfQuarter = Month.DECEMBER;
        } else if (reportReq.getQuarter() == Quarter.FIRST) {
            firstMonthOfQuarter = Month.JANUARY;
            lastMonthOfQuarter = Month.MARCH;
        } else if (reportReq.getQuarter() == Quarter.SECOND) {
            firstMonthOfQuarter = Month.APRIL;
            lastMonthOfQuarter = Month.JUNE;
        } else if (reportReq.getQuarter() == Quarter.THIRD) {
            firstMonthOfQuarter = Month.JULY;
            lastMonthOfQuarter = Month.SEPTEMBER;
        } else if (reportReq.getQuarter() == Quarter.FOURTH) {
            firstMonthOfQuarter = Month.OCTOBER;
            lastMonthOfQuarter = Month.DECEMBER;
        }
        LocalDate firstDayOfQuarter = LocalDate.of(year, firstMonthOfQuarter, 1);
        LocalDate lastDayOfQuarter = LocalDate.of(
                year,
                lastMonthOfQuarter,
                lastMonthOfQuarter.length(firstDayOfQuarter.isLeapYear()));
        List<LocalDate> firstAndLast = new ArrayList<>();
        firstAndLast.add(firstDayOfQuarter);
        firstAndLast.add(lastDayOfQuarter);
        return firstAndLast;
    }
}
