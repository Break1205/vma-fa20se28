package com.fa20se28.vma.configuration;

import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.RevenueExpense;
import com.fa20se28.vma.model.Schedule;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ExcelUtil {
    private Workbook workbook;
    private Sheet sheet;
    private final List<LocalDate> firstAndLast;
    private static final int TITLE_ROW = 0;
    private static final int HEADER_ROW = 1;
    private final ReportMapper reportMapper;

    public ExcelUtil(List<LocalDate> firstAndLast, ReportMapper reportMapper) {
        this.firstAndLast = firstAndLast;
        this.reportMapper = reportMapper;
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
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTOR_INCOME)) {
            writeContributorIncomesHeaderLine(reportReq, style);
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
            writeScheduleDataLines(style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE)) {
            writeMaintenanceByVehicleIdDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.MAINTENANCE_ALL_VEHICLES)) {
            writeMaintenanceAllVehicleDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRACTS)) {
            writeContractsDataLine(style);
        } else if (reportReq.getReportType().equals(ReportType.VEHICLE_REVENUE_EXPENSE)) {
            writeVehicleRevenueExpenseDataLine(reportReq, style);
        } else if (reportReq.getReportType().equals(ReportType.COMPANY_REVENUE_EXPENSE)) {
            writeCompanyRevenueExpenseDataLine(style);
        } else if (reportReq.getReportType().equals(ReportType.CONTRIBUTOR_INCOME)) {
            writeContributorIncomesDataLine(reportReq, style);
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

    private void writeScheduleDataLines(CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;
        List<Schedule> schedules =
                reportMapper.getListSchedule(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString());

        for (Schedule schedule : schedules) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, schedule.getVehicleId(), style);
            createCell(row, columnCount++, schedule.getDepartureLocation(), style);
            createCell(row, columnCount++, schedule.getDepartureTime().toString(), style);
            createCell(row, columnCount++, schedule.getDestinationLocation(), style);
            createCell(row, columnCount++, schedule.getDestinationTime().toString(), style);
            createCell(row, columnCount++, schedule.getContractVehicleStatus().toString(), style);
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

        List<MaintenanceReport> maintenanceReports =
                reportMapper.getMaintenanceByVehicleIdForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId());

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

        List<MaintenanceReport> maintenanceReports =
                reportMapper.getMaintenanceByVehicleIdForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId());

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

        List<RevenueExpense> vehicleRevenueExpenses =
                reportMapper.getVehicleRevenueExpenseForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId());
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

    private void writeCompanyRevenueExpenseDataLine(CellStyle style) {
        int rowCount = HEADER_ROW + 2;
        int numberOfData = 1;

        List<RevenueExpense> companyRevenueExpense =
                reportMapper.getCompanyRevenueExpenseForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString());
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
        createCell(vehicleIdRow, 1, reportReq.getContributorId(), style);

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

        List<ContributorIncome> contributorIncomes =
                reportMapper.getContributorIncomesForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getContributorId());
        for (ContributorIncome contributorIncome : contributorIncomes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, numberOfData++, style);
            createCell(row, columnCount++, contributorIncome.getVehicleId(), style);
            createCell(row, columnCount++, contributorIncome.getDate().toString(), style);
            createCell(row, columnCount++, contributorIncome.getValue(), style);
            createCell(row, columnCount, contributorIncome.getContractId(), style);
        }

        Row row = sheet.createRow(++rowCount);
        createCell(row, 2, "Total Value", style);
        Cell valueCell = row.createCell(3);
        valueCell.setCellFormula("SUM(D5:D" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
    }
    // end Contributor Income

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

        Row row = sheet.createRow(++rowCount);
        createCell(row, 1, "Total Value", style);
        Cell valueCell = row.createCell(2);
        valueCell.setCellFormula("SUM(C4:C" + (rowCount - 1) + ")");
        valueCell.setCellStyle(style);
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
}
