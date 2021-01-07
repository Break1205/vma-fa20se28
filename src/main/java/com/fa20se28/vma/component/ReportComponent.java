package com.fa20se28.vma.component;

import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.DriverIncome;
import com.fa20se28.vma.model.EstimateAndEarnedIncome;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.DriverIncomeRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportComponent {
    ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException;

    List<Schedule> getScheduleReportData(ReportReq reportReq);

    List<VehicleReport> getVehicleReportData(ReportReq reportReq);

    List<MaintenanceReport> getMaintenanceReportData(ReportReq reportReq);

    List<ContractReport> getContractsReportData(ReportReq reportReq);

    RevenueExpenseReportRes getRevenueExpenseDetailReportData(ReportReq reportReq);

    Map<String, EstimateAndEarnedIncome> calculateContributorEstimatedAndEarnedIncome(ReportReq reportReq);

    ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncomeById(ReportReq reportReq);

    List<ContributorIncome> getContributorIncomesDetails(ReportReq reportReq);

    List<DriverIncome> getDriversIncome(ReportReq reportReq);

    DriverIncomeRes getDriversIncomeById(ReportReq reportReq);

    float getDriverBaseSalary(String userId);
}
