package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public void exportReportByType(HttpServletResponse response,
                                   @RequestParam(required = false) String year,
                                   @RequestParam(required = false) String month,
                                   @RequestParam(required = false, defaultValue = "MONTH_SCHEDULE") ReportType reportType) throws IOException {
        reportService.exportReportByType(response, new ReportReq(year, month, reportType));
    }
}
