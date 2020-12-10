package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.ContributorIncomesDetail;
import com.fa20se28.vma.model.DriverIncomes;
import com.fa20se28.vma.model.EstimateAndEarnedIncome;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.RevenueExpense;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.DriverIncomeRes;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportComponentImpl implements ReportComponent {
    //excel
    private Workbook workbook;
    private Sheet sheet;
    private List<LocalDate> firstAndLast;
    private static final int TITLE_ROW = 0;
    private static final int HEADER_ROW = 1;
    private final ReportMapper reportMapper;

    public ReportComponentImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    @Override
    public ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException {
        firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return export(reportReq);
    }

    public ByteArrayInputStreamWrapper export(ReportReq reportReq) throws IOException {
        workbook = new HSSFWorkbook();
        writeTitleLine(reportReq);
        writeHeaderLine(reportReq);
        writeDataLines(reportReq);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        ByteArrayInputStreamWrapper inputStreamWrapper = new ByteArrayInputStreamWrapper();
        inputStreamWrapper.setByteArrayInputStream(new ByteArrayInputStream(out.toByteArray()));
        inputStreamWrapper.setByteCount(out.toByteArray().length);
        out.close();
        return inputStreamWrapper;
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
            writeScheduleHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE)) {
            writeMaintenanceByVehicleIdHeaderLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE_ALL_VEHICLES)) {
            writeMaintenanceAllVehicleHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRACTS)) {
            writeContractsHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLE_REVENUE_EXPENSE)) {
            writeVehicleRevenueExpenseHeaderLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.COMPANY_REVENUE_EXPENSE)) {
            writeCompanyRevenueExpenseHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTOR_INCOMES)) {
            writeContributorIncomesHeaderLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTORS_INCOMES)) {
            writeContributorsIncomesHeaderLine(style);
        } else if (reportReq.getReportType().equals(ReportType.DRIVER_INCOMES)) {
            writeDriverIncomesHeaderLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.DRIVERS_INCOMES)) {
            writeDriversIncomesHeaderLine(style);
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
            writeScheduleDataLines(style, reportReq);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE)) {
            writeMaintenanceByVehicleIdDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE_ALL_VEHICLES)) {
            writeMaintenanceAllVehicleDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRACTS)) {
            writeContractsDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLE_REVENUE_EXPENSE)) {
            writeVehicleRevenueExpenseDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.COMPANY_REVENUE_EXPENSE)) {
            writeCompanyRevenueExpenseDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTOR_INCOMES)) {
            writeContributorIncomesDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTORS_INCOMES)) {
            writeContributorsIncomesDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.DRIVER_INCOMES)) {
            writeDriverIncomesDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.DRIVERS_INCOMES)) {
            writeDriversIncomesDataLine(reportReq, style);
        }
    }

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

        List<VehicleReport> vehicleReports = getVehicleReportData();
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

    // Schedule
    private void writeScheduleHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Vehicle Id", style);
        createCell(row, 2, "From", style);
        createCell(row, 3, "Time", style);
        createCell(row, 4, "To", style);
        createCell(row, 5, "Time", style);
        createCell(row, 6, "Status", style);
    }

    private void writeScheduleDataLines(CellStyle style, ReportReq reportReq) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<Schedule> schedules = getScheduleReportData(reportReq);

        for (Schedule schedule : schedules) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, schedule.getVehicleId(), style);
            createCell(row, columnCount++, schedule.getDepartureLocation(), style);
            createCell(row, columnCount++, schedule.getDepartureTime().toString(), style);
            createCell(row, columnCount++, schedule.getDestinationLocation(), style);
            createCell(row, columnCount++, schedule.getDestinationTime().toString(), style);
            createCell(row, columnCount, schedule.getContractVehicleStatus().toString(), style);
        }
    }
    // end Schedule

    // Maintenance
    private void writeMaintenanceByVehicleIdHeaderLine(ReportReq reportReq, CellStyle style) {
        Row vehicleIdRow = sheet.createRow(HEADER_ROW);

        createCell(vehicleIdRow, 0, "Vehicle Id:", style);
        createCell(vehicleIdRow, 1, reportReq.getVehicleId(), style);

        Row rowFromAndTo = sheet.createRow(HEADER_ROW + 1);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 2);
        createCell(row, 0, "No", style);
        createCell(row, 1, "Maintenance Type", style);
        createCell(row, 2, "Start Date", style);
        createCell(row, 3, "End Date", style);
        createCell(row, 4, "Cost", style);
        createCell(row, 5, "Description", style);
    }

    private void writeMaintenanceAllVehicleHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);
        createCell(row, 0, "No", style);
        createCell(row, 1, "Vehicle Id", style);
        createCell(row, 2, "Maintenance Type", style);
        createCell(row, 3, "Start Date", style);
        createCell(row, 4, "End Date", style);
        createCell(row, 5, "Cost", style);
        createCell(row, 6, "Description", style);
    }

    private void writeMaintenanceByVehicleIdDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 3;
        int numberOfData = 1;

        List<MaintenanceReport> maintenanceReports = getMaintenanceReportData(reportReq);

        for (MaintenanceReport maintenanceReport : maintenanceReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, maintenanceReport.getMaintenanceType().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getStartDate().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getEndDate().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getCost(), style);
            createCell(row, columnCount, maintenanceReport.getDescription(), style);
        }

        Row row = sheet.createRow(rowCount);
        createCell(row, 3, "Total Cost", style);
        Cell valueCell = row.createCell(4);
        valueCell.setCellFormula("SUM(E5:E" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }

    private void writeMaintenanceAllVehicleDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<MaintenanceReport> maintenanceReports = getMaintenanceReportData(reportReq);

        for (MaintenanceReport maintenanceReport : maintenanceReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, maintenanceReport.getVehicleId(), style);
            createCell(row, columnCount++, maintenanceReport.getMaintenanceType().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getStartDate().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getEndDate().toString(), style);
            createCell(row, columnCount++, maintenanceReport.getCost(), style);
            createCell(row, columnCount, maintenanceReport.getDescription(), style);
        }

        Row row = sheet.createRow(rowCount);
        createCell(row, 4, "Total Cost", style);
        Cell valueCell = row.createCell(5);
        valueCell.setCellFormula("SUM(F4:F" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
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
        createCell(row, 3, "Departure Time", style);
        createCell(row, 4, "Departure Location", style);
        createCell(row, 5, "Destination Location", style);
        createCell(row, 6, "Customer Id", style);
        createCell(row, 7, "Customer Name", style);
        createCell(row, 8, "Phone Number", style);
        createCell(row, 9, "Email", style);
        createCell(row, 10, "Fax", style);
        createCell(row, 11, "Account Number", style);
        createCell(row, 12, "Tax Code", style);
    }

    private void writeContractsDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<ContractReport> contractReports = getContractsReportData(reportReq);

        for (ContractReport contractReport : contractReports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, contractReport.getContractId(), style);
            createCell(row, columnCount++, contractReport.getContractValue(), style);
            createCell(row, columnCount++, contractReport.getDepartureTime().toString(), style);
            createCell(row, columnCount++, contractReport.getDepartureLocation(), style);
            createCell(row, columnCount++, contractReport.getDestinationLocation(), style);
            createCell(row, columnCount++, contractReport.getCustomerId(), style);
            createCell(row, columnCount++, contractReport.getCustomerName(), style);
            createCell(row, columnCount++, contractReport.getPhoneNumber(), style);
            createCell(row, columnCount++, contractReport.getEmail(), style);
            createCell(row, columnCount++, contractReport.getFax(), style);
            createCell(row, columnCount++, contractReport.getAccountNumber(), style);
            createCell(row, columnCount, contractReport.getTaxCode(), style);
        }

        Row row = sheet.createRow(++rowCount);
        createCell(row, 1, "Total Value", style);
        Cell valueCell = row.createCell(2);
        valueCell.setCellFormula("SUM(C4:C" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }

    // Vehicle Revenue Expense
    private void writeVehicleRevenueExpenseHeaderLine(ReportReq reportReq, CellStyle style) {
        Row vehicleIdRow = sheet.createRow(HEADER_ROW);
        createCell(vehicleIdRow, 0, "Vehicle Id:", style);
        createCell(vehicleIdRow, 1, reportReq.getVehicleId(), style);

        Row rowFromAndTo = sheet.createRow(HEADER_ROW + 1);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 2);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Type", style);
        createCell(row, 3, "Value", style);
        createCell(row, 4, "Contract Id", style);
        createCell(row, 5, "Customer Id", style);
    }

    private void writeVehicleRevenueExpenseDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 3;
        int numberOfData = 1;

        List<RevenueExpense> vehicleRevenueExpenses = getVehicleRevenueExpenseReportData(reportReq);

        for (RevenueExpense revenueExpense : vehicleRevenueExpenses) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, revenueExpense.getDate().toString(), style);
            createCell(row, columnCount++, revenueExpense.getType(), style);
            createCell(row, columnCount++,
                    revenueExpense.getType().equals("CONTRACT REVENUE")
                            ? revenueExpense.getValue()
                            : -revenueExpense.getValue(), style);
            createCell(row, columnCount++, revenueExpense.getContractId(), style);
            createCell(row, columnCount, revenueExpense.getCustomerId(), style);
        }

        Row row = sheet.createRow(++rowCount);
        createCell(row, 2, "Total Value", style);
        Cell valueCell = row.createCell(3);
        valueCell.setCellFormula("SUM(D5:D" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }

    // end Vehicle Revenue Expense


    // Company Revenue Expense
    private void writeCompanyRevenueExpenseHeaderLine(CellStyle style) {
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

    private void writeCompanyRevenueExpenseDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<RevenueExpense> companyRevenueExpense = getCompanyRevenueExpenseReportData(reportReq);
        for (RevenueExpense revenueExpense : companyRevenueExpense) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, revenueExpense.getDate().toString(), style);
            createCell(row, columnCount++, revenueExpense.getType(), style);
            createCell(row, columnCount++,
                    revenueExpense.getType().equals("CONTRACT REVENUE")
                            ? revenueExpense.getValue()
                            : -revenueExpense.getValue(), style);
            createCell(row, columnCount++, revenueExpense.getContractId(), style);
            createCell(row, columnCount, revenueExpense.getCustomerId(), style);
        }

        Row row = sheet.createRow(++rowCount);
        createCell(row, 2, "Total Value", style);
        Cell valueCell = row.createCell(3);
        valueCell.setCellFormula("SUM(D4:D" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }
    // end Company Revenue Expense

    // Contributor Income
    private void writeContributorIncomesHeaderLine(ReportReq reportReq, CellStyle style) {
        Row vehicleIdRow = sheet.createRow(HEADER_ROW);
        createCell(vehicleIdRow, 0, "Contributor Id:", style);
        createCell(vehicleIdRow, 1, reportReq.getUserId(), style);

        Row rowFromAndTo = sheet.createRow(HEADER_ROW + 1);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 2);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Vehicle Id", style);
        createCell(row, 2, "Date", style);
        createCell(row, 3, "Value", style);
        createCell(row, 4, "Contract Id", style);
    }

    private void writeContributorIncomesDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 3;
        int numberOfData = 1;

        List<ContributorIncomesDetail> contributorIncomesDetails = getContributorIncomesDetails(reportReq);
        for (ContributorIncomesDetail contributorIncomesDetail : contributorIncomesDetails) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, contributorIncomesDetail.getVehicleId(), style);
            createCell(row, columnCount++, contributorIncomesDetail.getDate().toString(), style);
            createCell(row, columnCount++, contributorIncomesDetail.getValue(), style);
            createCell(row, columnCount, contributorIncomesDetail.getContractId(), style);
        }

        Row row = sheet.createRow(rowCount);
        createCell(row, 2, "Total Value", style);
        Cell valueCell = row.createCell(3);
        valueCell.setCellFormula("SUM(D5:D" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }

    private void writeContributorsIncomesHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Contributor Id", style);
        createCell(row, 2, "Total Estimated Value", style);
        createCell(row, 3, "Total Value", style);
    }

    private void writeContributorsIncomesDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        Map<String, EstimateAndEarnedIncome> contributorsEstimateAndRealValue =
                calculateContributorEstimatedAndEarnedIncome(reportReq);

        for (Map.Entry<String, EstimateAndEarnedIncome> entry : contributorsEstimateAndRealValue.entrySet()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, entry.getKey(), style);
            createCell(row, columnCount++, entry.getValue().getEstimatedValue(), style);
            createCell(row, columnCount, entry.getValue().getEarnedValue(), style);
        }
    }

    // end Contributor Income

    // start driver income
    private void writeDriverIncomesHeaderLine(ReportReq reportReq, CellStyle style) {
        Row vehicleIdRow = sheet.createRow(HEADER_ROW);
        createCell(vehicleIdRow, 0, "Driver Id:", style);
        createCell(vehicleIdRow, 1, reportReq.getUserId(), style);

        Row rowFromAndTo = sheet.createRow(HEADER_ROW + 2);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 3);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Contract Id", style);
        createCell(row, 2, "Vehicle Id", style);
        createCell(row, 3, "Driver Earned", style);
    }

    private void writeDriverIncomesDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 4;
        int numberOfData = 1;

        List<DriverIncomes> driverIncomes = getDriversIncome(reportReq);
        for (DriverIncomes driverIncomesDetail : driverIncomes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, driverIncomesDetail.getContractId(), style);
            createCell(row, columnCount++, driverIncomesDetail.getVehicleId(), style);
            createCell(row, columnCount, driverIncomesDetail.getDriverEarned(), style);
        }

        Row row = sheet.createRow(rowCount);
        createCell(row, 2, "Total Earned", style);
        Cell valueCell = row.createCell(3);
        valueCell.setCellFormula("SUM(D6:D" + (rowCount) + ")");
        valueCell.setCellStyle(style);

        CellStyle baseSalaryStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 320);
        baseSalaryStyle.setFont(font);

        Row baseSalaryRow = sheet.createRow(HEADER_ROW + 1);
        createCell(baseSalaryRow, 0, "Base Salary", baseSalaryStyle);
        createCell(baseSalaryRow, 1, driverIncomes.get(0) != null ? driverIncomes.get(0).getBaseSalary() : "N/A", baseSalaryStyle);

        Row totalSalaryRow = sheet.createRow(rowCount + 1);
        createCell(totalSalaryRow, 2, "Total Salary", style);
        Cell totalSalaryCell = totalSalaryRow.createCell(3);
        totalSalaryCell.setCellFormula("B3+D" + (rowCount + 1));
        totalSalaryCell.setCellStyle(style);
    }

    private void writeDriversIncomesHeaderLine(CellStyle style) {
        Row rowFromAndTo = sheet.createRow(HEADER_ROW);

        createCell(rowFromAndTo, 0, "From", style);
        createCell(rowFromAndTo, 1, firstAndLast.get(0).toString(), style);
        createCell(rowFromAndTo, 2, "To", style);
        createCell(rowFromAndTo, 3, firstAndLast.get(1).toString(), style);

        Row row = sheet.createRow(HEADER_ROW + 1);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Driver Id", style);
        createCell(row, 2, "Contract Id", style);
        createCell(row, 3, "Vehicle Id", style);
        createCell(row, 4, "Driver Earned", style);
    }


    private void writeDriversIncomesDataLine(ReportReq reportReq, CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<DriverIncomes> driverIncomes = getDriversIncome(reportReq);
        for (DriverIncomes driverIncomesDetail : driverIncomes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, driverIncomesDetail.getUserId(), style);
            createCell(row, columnCount++, driverIncomesDetail.getContractId(), style);
            createCell(row, columnCount++, driverIncomesDetail.getVehicleId(), style);
            createCell(row, columnCount, driverIncomesDetail.getDriverEarned(), style);
        }
    }
    // end driver income

    @Override
    public List<Schedule> getScheduleReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getListSchedule(
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
    }

    @Override
    public List<VehicleReport> getVehicleReportData() {
        return reportMapper.getVehiclesForReport();
    }

    @Override
    public List<MaintenanceReport> getMaintenanceReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getMaintenanceByVehicleIdForReport(
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString(),
                reportReq.getVehicleId());
    }

    @Override
    public List<ContractReport> getContractsReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getContractsReport(
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
    }

    @Override
    public List<RevenueExpense> getVehicleRevenueExpenseReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getVehicleRevenueExpenseForReport(
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString(),
                reportReq.getVehicleId());
    }

    @Override
    public List<RevenueExpense> getCompanyRevenueExpenseReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getCompanyRevenueExpenseForReport(
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
    }

    @Override
    public Map<String, EstimateAndEarnedIncome> calculateContributorEstimatedAndEarnedIncome(ReportReq reportReq) {
        firstAndLast = getFirstAndLastDayInAMonth(reportReq);

        List<ContributorIncome> contributorIncomes = getContributorsIncomeReportData(reportReq);
        List<ContributorIncomesDetail> contributorIncomesDetails = getContributorIncomesDetails(reportReq);

        Map<String, List<ContributorIncome>> contributorsEstimatedIncomesMap = new HashMap<>();
        Map<String, EstimateAndEarnedIncome> contributorsEstimateAndEarnedIncomes = new HashMap<>();

        // calculate EarnedValue
        for (ContributorIncomesDetail contributorIncomesDetail : contributorIncomesDetails) {
            if (contributorsEstimateAndEarnedIncomes.containsKey(contributorIncomesDetail.getOwnerId())) {
                Float earnedMoney =
                        contributorIncomesDetail.getValue() +
                                contributorsEstimateAndEarnedIncomes.get(contributorIncomesDetail.getOwnerId()).getEarnedValue();
                contributorsEstimateAndEarnedIncomes.get(contributorIncomesDetail.getOwnerId()).setEarnedValue(earnedMoney);
            } else {
                contributorsEstimateAndEarnedIncomes.put(contributorIncomesDetail.getOwnerId(), new EstimateAndEarnedIncome());
                contributorsEstimateAndEarnedIncomes.get(contributorIncomesDetail.getOwnerId()).setEarnedValue(contributorIncomesDetail.getValue());
            }
        }

        // calculated EstimatedValue
        for (ContributorIncome contributorIncome : contributorIncomes) {
            if (!contributorsEstimatedIncomesMap.containsKey(contributorIncome.getOwnerId())) {
                contributorsEstimatedIncomesMap.put(contributorIncome.getOwnerId(), new ArrayList<>());
            }
            contributorsEstimatedIncomesMap.get(contributorIncome.getOwnerId()).add(contributorIncome);
        }

        long totalDaysInThisQuarter = firstAndLast.get(0).until(firstAndLast.get(1), ChronoUnit.DAYS);

        for (Map.Entry<String, List<ContributorIncome>> entry : contributorsEstimatedIncomesMap.entrySet()) {
            float estimatedValue = 0;
            for (ContributorIncome contributorIncome : entry.getValue()) {
                long totalDays;
                if (contributorIncome.getStartDate().isBefore(firstAndLast.get(0))
                        && contributorIncome.getEndDate().isAfter(firstAndLast.get(1))) {
                    estimatedValue += (contributorIncome.getValue() / 30) * totalDaysInThisQuarter;
                } else if (firstAndLast.get(0).isBefore(contributorIncome.getStartDate())
                        && firstAndLast.get(1).isAfter(contributorIncome.getEndDate())) {
                    totalDays = contributorIncome.getStartDate().until(contributorIncome.getEndDate(), ChronoUnit.DAYS);
                    estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
                } else {
                    totalDays = firstAndLast.get(0).until(contributorIncome.getEndDate(), ChronoUnit.DAYS);
                    if (totalDays <= totalDaysInThisQuarter) {
                        estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
                    }
                    totalDays = contributorIncome.getStartDate().until(firstAndLast.get(1), ChronoUnit.DAYS);
                    if (totalDays <= totalDaysInThisQuarter) {
                        estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
                    }
                }
            }
            if (!contributorsEstimateAndEarnedIncomes.containsKey(entry.getKey())) {
                contributorsEstimateAndEarnedIncomes.put(entry.getKey(), new EstimateAndEarnedIncome());
            }
            contributorsEstimateAndEarnedIncomes.get(entry.getKey()).setEstimatedValue(estimatedValue);
        }
        return contributorsEstimateAndEarnedIncomes;
    }

    private List<ContributorIncome> getContributorsIncomeReportData(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getContributorIncomesForReport(
                null,
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
    }

    @Override
    public ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncomeById(ReportReq reportReq) {
        firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        List<ContributorIncome> contributorIncomes =
                reportMapper.getContributorIncomesForReport(
                        reportReq.getUserId(),
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString());

        long totalDaysInThisQuarter = firstAndLast.get(0).until(firstAndLast.get(1), ChronoUnit.DAYS);
        // calculate estimated

        float estimatedValue = 0;
        for (ContributorIncome contributorIncome : contributorIncomes) {
            long totalDays;
            if (contributorIncome.getStartDate().isBefore(firstAndLast.get(0))
                    && contributorIncome.getEndDate().isAfter(firstAndLast.get(1))) {
                estimatedValue += (contributorIncome.getValue() / 30) * totalDaysInThisQuarter;
            } else if (firstAndLast.get(0).isBefore(contributorIncome.getStartDate())
                    && firstAndLast.get(1).isAfter(contributorIncome.getEndDate())) {
                totalDays = contributorIncome.getStartDate().until(contributorIncome.getEndDate(), ChronoUnit.DAYS);
                estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
            } else {
                totalDays = firstAndLast.get(0).until(contributorIncome.getEndDate(), ChronoUnit.DAYS);
                if (totalDays <= totalDaysInThisQuarter) {
                    estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
                }
                totalDays = contributorIncome.getStartDate().until(firstAndLast.get(1), ChronoUnit.DAYS);
                if (totalDays <= totalDaysInThisQuarter) {
                    estimatedValue += (contributorIncome.getValue() / 30) * totalDays;
                }
            }
        }
        // end
        List<ContributorIncomesDetail> contributorIncomesDetails = reportMapper.getContributorIncomesDetail(
                reportReq.getUserId(),
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
        // calculate earned
        float earnedValue = 0;
        for (ContributorIncomesDetail contributorIncomesDetail : contributorIncomesDetails) {
            earnedValue += contributorIncomesDetail.getValue();
        }
        // end
        ContributorEarnedAndEstimatedIncome contributorEarnedAndEstimatedIncome =
                new ContributorEarnedAndEstimatedIncome();
        contributorEarnedAndEstimatedIncome.setContributorIncomesDetails(contributorIncomesDetails);
        contributorEarnedAndEstimatedIncome.setEstimated(estimatedValue);
        contributorEarnedAndEstimatedIncome.setEarned(earnedValue);
        return contributorEarnedAndEstimatedIncome;
    }

    @Override
    public List<DriverIncomes> getDriversIncome(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getDriverIncomes(
                null,
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
    }

    @Override
    public DriverIncomeRes getDriversIncomeById(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        List<DriverIncomes> driverIncomes = reportMapper.getDriverIncomes(
                reportReq.getUserId(),
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
        DriverIncomeRes driverIncomeRes = new DriverIncomeRes();
        driverIncomeRes.setDriverIncomes(driverIncomes);
        float earnedValue = 0;
        for (DriverIncomes driverIncomesDetail : driverIncomes) {
            earnedValue += driverIncomesDetail.getDriverEarned();
        }
        if (!driverIncomes.isEmpty()) {
            earnedValue += driverIncomes.get(0).getBaseSalary();
        }
        driverIncomeRes.setEarnedValue(earnedValue);
        return driverIncomeRes;
    }

    @Override
    public float getDriverBaseSalary(String userId) {
        return reportMapper.getDriverBaseSalary(userId);
    }

    @Override
    public List<ContributorIncomesDetail> getContributorIncomesDetails(ReportReq reportReq) {
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        return reportMapper.getContributorIncomesDetail(
                reportReq.getUserId(),
                firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString());
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
        } else if (reportReq.getQuarter() == Quarter.JANUARY) {
            firstMonthOfQuarter = Month.JANUARY;
            lastMonthOfQuarter = Month.JANUARY;
        } else if (reportReq.getQuarter() == Quarter.FEBRUARY) {
            firstMonthOfQuarter = Month.FEBRUARY;
            lastMonthOfQuarter = Month.FEBRUARY;
        } else if (reportReq.getQuarter() == Quarter.MARCH) {
            firstMonthOfQuarter = Month.MARCH;
            lastMonthOfQuarter = Month.MARCH;
        } else if (reportReq.getQuarter() == Quarter.APRIL) {
            firstMonthOfQuarter = Month.APRIL;
            lastMonthOfQuarter = Month.APRIL;
        } else if (reportReq.getQuarter() == Quarter.MAY) {
            firstMonthOfQuarter = Month.MAY;
            lastMonthOfQuarter = Month.MAY;
        } else if (reportReq.getQuarter() == Quarter.JUNE) {
            firstMonthOfQuarter = Month.JUNE;
            lastMonthOfQuarter = Month.JUNE;
        } else if (reportReq.getQuarter() == Quarter.JULY) {
            firstMonthOfQuarter = Month.JULY;
            lastMonthOfQuarter = Month.JULY;
        } else if (reportReq.getQuarter() == Quarter.AUGUST) {
            firstMonthOfQuarter = Month.AUGUST;
            lastMonthOfQuarter = Month.AUGUST;
        } else if (reportReq.getQuarter() == Quarter.SEPTEMBER) {
            firstMonthOfQuarter = Month.SEPTEMBER;
            lastMonthOfQuarter = Month.SEPTEMBER;
        } else if (reportReq.getQuarter() == Quarter.OCTOBER) {
            firstMonthOfQuarter = Month.OCTOBER;
            lastMonthOfQuarter = Month.OCTOBER;
        } else if (reportReq.getQuarter() == Quarter.NOVEMBER) {
            firstMonthOfQuarter = Month.NOVEMBER;
            lastMonthOfQuarter = Month.NOVEMBER;
        } else if (reportReq.getQuarter() == Quarter.DECEMBER) {
            firstMonthOfQuarter = Month.DECEMBER;
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
