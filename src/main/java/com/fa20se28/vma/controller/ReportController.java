package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.Quarter;
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
                                     @RequestParam(required = false) Quarter quarter,
                                     @RequestParam(required = false) Integer year) throws IOException {
        reportService.exportReportByType(response,
                new ReportReq(null, null, year, quarter, ReportType.SCHEDULE));
    }

    @GetMapping("vehicles")
    public void exportVehiclesReport(HttpServletResponse response) throws IOException {
        reportService.exportReportByType(response,
                new ReportReq(null, null, null, null, ReportType.VEHICLES));
    }


    @GetMapping("maintenances")
    public void exportMaintenancesReport(HttpServletResponse response,
                                         @RequestParam(required = false) String vehicleId,
                                         @RequestParam(required = false) Quarter quarter,
                                         @RequestParam(required = false) Integer year) throws IOException {
        reportService.exportReportByType(response,
                new ReportReq(null, vehicleId, year, quarter, ReportType.MAINTENANCE));
    }

    @GetMapping("contracts")
    public void exportContractsReport(HttpServletResponse response,
                                      @RequestParam(required = false) Quarter quarter,
                                      @RequestParam(required = false) Integer year) throws IOException {
        reportService.exportReportByType(response,
                new ReportReq(null, null, year, quarter, ReportType.CONTRACTS));
    }

    @GetMapping("revenue-expense")
    public void exportRevenueExpenseReport(HttpServletResponse response,
                                           @RequestParam(required = false) String vehicleId,
                                           @RequestParam(required = false) Quarter quarter,
                                           @RequestParam(required = false) Integer year) throws IOException {
        if (vehicleId != null) {
            reportService.exportReportByType(response,
                    new ReportReq(null, vehicleId, year, quarter, ReportType.VEHICLE_REVENUE_EXPENSE));
        } else {
            reportService.exportReportByType(response,
                    new ReportReq(null, null, year, quarter, ReportType.COMPANY_REVENUE_EXPENSE));
        }
    }

    @GetMapping("contributor-income")
    public void exportContributorIncomeReport(HttpServletResponse response,
                                              @RequestParam(required = false) String contributorId,
                                              @RequestParam(required = false) Quarter quarter,
                                              @RequestParam(required = false) Integer year) throws IOException {
        reportService.exportReportByType(response,
                new ReportReq(contributorId, null, year, quarter, ReportType.CONTRIBUTOR_INCOME));
    }
}
