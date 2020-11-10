package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.request.ReportReq;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ReportComponentImpl implements ReportComponent {
    private Workbook workbook;
    private Sheet sheet;

    public ReportComponentImpl() {
        workbook = new HSSFWorkbook();
    }

    @Override
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        export(response, reportReq);
    }

    private void writeHeaderLine(ReportReq reportReq) {
        sheet = workbook.createSheet("Monthly Revenue Report");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 320);
        style.setFont(font);

    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 280);
        style.setFont(font);

//        for (User user : listUsers) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//
//            createCell(row, columnCount++, user.getId(), style);
//            createCell(row, columnCount++, user.getEmail(), style);
//            createCell(row, columnCount++, user.getFullName(), style);
//            createCell(row, columnCount++, user.getRoles().toString(), style);
//            createCell(row, columnCount++, user.isEnabled(), style);
//
//        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void export(HttpServletResponse response, ReportReq reportReq) throws IOException {
        writeHeaderLine(reportReq);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private List<LocalDate> getTotalDatesInAMonth(ReportReq reportReq) {
        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? Integer.parseInt(reportReq.getYear()) : currentDate.getYear();
        LocalDate firstDayOfMonth = LocalDate.of(year, Integer.parseInt(reportReq.getMonth()),1);
        return null;
    }
}
