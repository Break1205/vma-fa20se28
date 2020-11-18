package com.fa20se28.vma.component;

import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
import com.fa20se28.vma.response.MaintenanceReportRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;
import com.fa20se28.vma.response.ScheduleRes;
import com.fa20se28.vma.response.VehicleReportRes;

import java.io.IOException;

public interface ReportComponent {
    ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException;

    ScheduleRes exportScheduleReportData(ReportReq reportReq);

    VehicleReportRes exportVehicleReportData(ReportReq reportReq);

    MaintenanceReportRes exportMaintenanceReportData(ReportReq reportReq);

    ContractReportRes exportContractsReportData(ReportReq reportReq);

    RevenueExpenseReportRes exportVehicleRevenueExpenseReportData(ReportReq reportReq);

    RevenueExpenseReportRes exportCompanyRevenueExpenseReportData(ReportReq reportReq);

    ContributorIncomeRes exportContributorIncomeReportData(ReportReq reportReq);
}
