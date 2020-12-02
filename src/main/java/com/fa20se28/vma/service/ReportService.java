package com.fa20se28.vma.service;

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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
    ResponseEntity<InputStreamResource> exportReportByType(ReportReq reportReq) throws IOException;

    ResponseEntity<InputStreamResource> exportPdfContractReport(int contractId) throws IOException;

    ScheduleRes getScheduleReportDate(ReportReq reportReq);

    VehicleReportRes getVehicleReportData();

    MaintenanceReportRes getMaintenanceReportData(ReportReq reportReq);

    ContractReportRes getContractsReportData(ReportReq reportReq);

    RevenueExpenseReportRes getVehicleRevenueExpenseReportData(ReportReq reportReq);

    RevenueExpenseReportRes getCompanyRevenueExpenseReportData(ReportReq reportReq);

    ContributorIncomeRes getContributorsIncomesReportData(ReportReq reportReq);

    ContributorIncomesDetailRes getContributorIncomesReportData(ReportReq reportReq);

    ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncome(ReportReq reportReq);

    DriversIncomeRes getDriversIncomesReportData(ReportReq reportReq);

    DriverIncomeRes getDriverEarnedAndEstimatedIncome(ReportReq reportReq);
}
