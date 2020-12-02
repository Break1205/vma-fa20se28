package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
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
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportComponent reportComponent;


    public ReportServiceImpl(ReportComponent reportComponent) {
        this.reportComponent = reportComponent;
    }

    @Override
    public ResponseEntity<InputStreamResource> exportReportByType(ReportReq reportReq) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=REPORT_" + reportReq.getReportType() + "_" + currentDateTime + ".xls";
        ByteArrayInputStreamWrapper inputStreamWrapper = reportComponent.exportReportByType(reportReq);

        return ResponseEntity
                .ok()
                .contentLength(inputStreamWrapper.getByteCount())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .cacheControl(CacheControl.noCache())
                .header(headerKey, headerValue)
                .body(new InputStreamResource(inputStreamWrapper.getByteArrayInputStream()));
    }

    @Override
    public ResponseEntity<InputStreamResource> exportPdfContractReport(int contractId) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CONTRACT_" + contractId + "_" + currentDateTime + ".xls";
        ByteArrayInputStreamWrapper inputStreamWrapper = reportComponent.exportPdfContractReport(contractId);

        return ResponseEntity
                .ok()
                .contentLength(inputStreamWrapper.getByteCount())
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header(headerKey, headerValue)
                .body(new InputStreamResource(inputStreamWrapper.getByteArrayInputStream()));
    }

    @Override
    public ScheduleRes getScheduleReportDate(ReportReq reportReq) {
        ScheduleRes scheduleRes = new ScheduleRes();
        scheduleRes.setSchedules(reportComponent.getScheduleReportData(reportReq));
        return scheduleRes;
    }

    @Override
    public VehicleReportRes getVehicleReportData() {
        VehicleReportRes vehicleReportRes = new VehicleReportRes();
        vehicleReportRes.setVehicleReports(reportComponent.getVehicleReportData());
        return vehicleReportRes;
    }

    @Override
    public MaintenanceReportRes getMaintenanceReportData(ReportReq reportReq) {
        MaintenanceReportRes maintenanceReportRes = new MaintenanceReportRes();
        maintenanceReportRes.setMaintenanceReports(reportComponent.getMaintenanceReportData(reportReq));
        return maintenanceReportRes;
    }

    @Override
    public ContractReportRes getContractsReportData(ReportReq reportReq) {
        ContractReportRes contractReportRes = new ContractReportRes();
        contractReportRes.setContractReports(reportComponent.getContractsReportData(reportReq));
        return contractReportRes;
    }

    @Override
    public RevenueExpenseReportRes getVehicleRevenueExpenseReportData(ReportReq reportReq) {
        RevenueExpenseReportRes revenueExpenseReportRes = new RevenueExpenseReportRes();
        revenueExpenseReportRes.setRevenueExpenses(reportComponent.getVehicleRevenueExpenseReportData(reportReq));
        return revenueExpenseReportRes;
    }

    @Override
    public RevenueExpenseReportRes getCompanyRevenueExpenseReportData(ReportReq reportReq) {
        RevenueExpenseReportRes revenueExpenseReportRes = new RevenueExpenseReportRes();
        revenueExpenseReportRes.setRevenueExpenses(reportComponent.getCompanyRevenueExpenseReportData(reportReq));
        return revenueExpenseReportRes;
    }

    @Override
    public ContributorIncomeRes getContributorsIncomesReportData(ReportReq reportReq) {
        ContributorIncomeRes contributorIncomeRes = new ContributorIncomeRes();
        contributorIncomeRes.setContributorEstimateAndEarnedIncomes(reportComponent.calculateContributorEstimatedAndEarnedIncome(reportReq));
        return contributorIncomeRes;
    }

    @Override
    public ContributorIncomesDetailRes getContributorIncomesReportData(ReportReq reportReq) {
        ContributorIncomesDetailRes contributorIncomesDetailRes = new ContributorIncomesDetailRes();
        contributorIncomesDetailRes.setContributorIncomesDetails(reportComponent.getContributorIncomesDetails(reportReq));
        return contributorIncomesDetailRes;
    }

    @Override
    public ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncome(ReportReq reportReq) {
        return reportComponent.getContributorEarnedAndEstimatedIncomeById(reportReq);
    }

    @Override
    public DriversIncomeRes getDriversIncomesReportData(ReportReq reportReq) {
        DriversIncomeRes driversIncomeRes = new DriversIncomeRes();
        driversIncomeRes.setDriverIncomes(reportComponent.getDriversIncome(reportReq));
        return driversIncomeRes;
    }

    @Override
    public DriverIncomeRes getDriverEarnedAndEstimatedIncome(ReportReq reportReq) {
        return reportComponent.getDriversIncomeById(reportReq);
    }
}
