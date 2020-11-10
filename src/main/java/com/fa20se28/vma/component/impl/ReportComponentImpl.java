package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
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
    private final CellStyle style;
    private final ReportMapper reportMapper;
    private static final int TITLE_ROW = 0;
    private static final int HEADER_ROW = 1;

    public ReportComponentImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
        workbook = new HSSFWorkbook();
        style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 320);
        style.setFont(font);
    }

    @Override
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        export(response, reportReq);
    }

    private void export(HttpServletResponse response, ReportReq reportReq) throws IOException {
        writeHeaderLine(reportReq);
        writeMonthScheduleDataLines(reportReq);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void writeHeaderLine(ReportReq reportReq) {
        sheet = workbook.createSheet(reportReq.getReportType().toString());
        Row row = sheet.createRow(TITLE_ROW);
        sheet.addMergedRegion(new CellRangeAddress(TITLE_ROW, TITLE_ROW, 0, 4));
        createCell(row, 0, reportReq.getReportType().toString(), style);
        writeMonthScheduleHeaderLine();

    }

    private void writeMonthScheduleHeaderLine() {
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


    private void writeMonthScheduleDataLines(ReportReq reportReq) {
        int rowCount = HEADER_ROW + 1;
        int numberOfData = 0;
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 280);
        style.setFont(font);

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
