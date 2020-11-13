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

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("schedule")
    public void exportScheduleReport(HttpServletResponse response,
                                     @RequestParam(required = false) String year,
                                     @RequestParam(required = false) String month) throws IOException {
        reportService.exportReportByType(response, new ReportReq(year, month, ReportType.SCHEDULE));
    }

    @GetMapping("vehicles")
    public void exportVehiclesReport(HttpServletResponse response) throws IOException {
        reportService.exportReportByType(response, new ReportReq("", "", ReportType.VEHICLES));
    }
}
