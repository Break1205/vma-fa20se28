package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
import com.fa20se28.vma.response.MaintenanceReportRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;
import com.fa20se28.vma.response.ScheduleRes;
import com.fa20se28.vma.response.VehicleReportRes;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
    ResponseEntity<InputStreamResource> exportReportByType(ReportReq reportReq) throws IOException;

    ScheduleRes exportScheduleReportDate(ReportReq reportReq);

    VehicleReportRes exportVehicleReportData(ReportReq reportReq);

    MaintenanceReportRes exportMaintenanceReportData(ReportReq reportReq);

    ContractReportRes exportContractsReportData(ReportReq reportReq);

    RevenueExpenseReportRes exportVehicleRevenueExpenseReportData(ReportReq reportReq);

    RevenueExpenseReportRes exportCompanyRevenueExpenseReportData(ReportReq reportReq);

    ContributorIncomeRes exportContributorIncomeReportData(ReportReq reportReq);
}
