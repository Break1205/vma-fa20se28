package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;
import com.fa20se28.vma.model.ContributorIncomesDetailRes;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
import com.fa20se28.vma.response.DriverIncomeRes;
import com.fa20se28.vma.response.DriversIncomeRes;
import com.fa20se28.vma.response.MaintenanceReportRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;
import com.fa20se28.vma.response.ScheduleRes;
import com.fa20se28.vma.response.VehicleReportRes;
import com.fa20se28.vma.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @GetMapping("contracts/{contract-id}")
//    public ResponseEntity<InputStreamResource> getContractById(@PathVariable("contract-id") int contractId) throws IOException {
//        return reportService.exportPdfContractReport(contractId);
//    }

    @GetMapping("schedule")
    public ResponseEntity<InputStreamResource> exportScheduleReport(@RequestParam(required = false) Quarter quarter,
                                                                    @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(null, null, year, quarter, ReportType.SCHEDULE));
    }

    @GetMapping("vehicles")
    public ResponseEntity<InputStreamResource> exportVehiclesReport() throws IOException {
        return reportService.exportReportByType(
                new ReportReq(null, null, null, null, ReportType.VEHICLES));
    }


    @GetMapping("maintenance")
    public ResponseEntity<InputStreamResource> exportMaintenanceReport(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        if (vehicleId != null) {
            return reportService.exportReportByType(
                    new ReportReq(null, vehicleId, year, quarter, ReportType.MAINTENANCE));
        } else {
            return reportService.exportReportByType(
                    new ReportReq(null, vehicleId, year, quarter, ReportType.MAINTENANCE_ALL_VEHICLES));
        }
    }

    @GetMapping("contracts")
    public ResponseEntity<InputStreamResource> exportContractsReport(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(null, null, year, quarter, ReportType.CONTRACTS));
    }

    @GetMapping("revenue-expense")
    public ResponseEntity<InputStreamResource> exportRevenueExpenseReport(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        if (vehicleId != null) {
            return reportService.exportReportByType(
                    new ReportReq(null, vehicleId, year, quarter, ReportType.VEHICLE_REVENUE_EXPENSE));
        } else {
            return reportService.exportReportByType(
                    new ReportReq(null, null, year, quarter, ReportType.COMPANY_REVENUE_EXPENSE));
        }
    }

    @GetMapping("contributor-income")
    public ResponseEntity<InputStreamResource> exportContributorsIncomesReport(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(null, null, year, quarter, ReportType.CONTRIBUTORS_INCOMES));
    }

    @GetMapping("contributor-income/{contributor-id}")
    public ResponseEntity<InputStreamResource> exportContributorIncomesReport(
            @PathVariable("contributor-id") String contributorId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(contributorId, null, year, quarter, ReportType.CONTRIBUTOR_INCOMES));
    }

    @GetMapping("driver-income")
    public ResponseEntity<InputStreamResource> exportDriversIncomesReport(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(null, null, year, quarter, ReportType.DRIVERS_INCOMES));
    }

    @GetMapping("driver-income/{driver-id}")
    public ResponseEntity<InputStreamResource> exportDriverIncomesReport(
            @PathVariable("driver-id") String driverId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) throws IOException {
        return reportService.exportReportByType(
                new ReportReq(driverId, null, year, quarter, ReportType.DRIVER_INCOMES));
    }

    @GetMapping("schedule/data")
    public ScheduleRes getScheduleReportData(@RequestParam(required = false) Quarter quarter,
                                             @RequestParam(required = false) Integer year) {
        return reportService.getScheduleReportDate(
                new ReportReq(null, null, year, quarter, null));
    }

    @GetMapping("vehicles/data")
    public VehicleReportRes getVehiclesReportData() {
        return reportService.getVehicleReportData();
    }


    @GetMapping("maintenance/data")
    public MaintenanceReportRes getMaintenanceReportData(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getMaintenanceReportData(
                new ReportReq(null, vehicleId, year, quarter, null));
    }

    @GetMapping("contracts/data")
    public ContractReportRes getContractsReportData(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getContractsReportData(
                new ReportReq(null, null, year, quarter, null));
    }

    @GetMapping("revenues-expenses/data")
    public RevenueExpenseReportRes getRevenueExpenseReportData(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        if (vehicleId != null) {
            return reportService.getVehicleRevenueExpenseReportData(
                    new ReportReq(null, vehicleId, year, quarter, null));
        } else {
            return reportService.getCompanyRevenueExpenseReportData(
                    new ReportReq(null, null, year, quarter, null));
        }
    }

    @GetMapping("contributor-income/data")
    public ContributorIncomeRes getContributorsIncomesReportData(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getContributorsIncomesReportData(
                new ReportReq(null, null, year, quarter, null));

    }

    @GetMapping("contributor-income/{contributor-id}/data")
    public ContributorEarnedAndEstimatedIncome getContributorIncomesReportData(
            @PathVariable("contributor-id") String contributorId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getContributorEarnedAndEstimatedIncome(
                new ReportReq(contributorId, null, year, quarter, null));
    }

    @GetMapping("driver-income/data")
    public DriversIncomeRes getDriversIncomesReportData(
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getDriversIncomesReportData(
                new ReportReq(null, null, year, quarter, null));

    }

    @GetMapping("driver-income/{driver-id}/data")
    public DriverIncomeRes getDriverIncomesReportData(
            @PathVariable("driver-id") String driverId,
            @RequestParam(required = false) Quarter quarter,
            @RequestParam(required = false) Integer year) {
        return reportService.getDriverEarnedAndEstimatedIncome(
                new ReportReq(driverId, null, year, quarter, null));
    }
}
