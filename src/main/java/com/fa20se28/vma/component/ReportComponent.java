package com.fa20se28.vma.component;

import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;
import com.fa20se28.vma.model.ContributorIncomesDetail;
import com.fa20se28.vma.model.DriverIncomes;
import com.fa20se28.vma.model.EstimateAndEarnedIncome;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.RevenueExpense;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.DriverIncomeRes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportComponent {
    ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException;

    List<Schedule> getScheduleReportData(ReportReq reportReq);

    List<VehicleReport> getVehicleReportData();

    List<MaintenanceReport> getMaintenanceReportData(ReportReq reportReq);

    List<ContractReport> getContractsReportData(ReportReq reportReq);

    List<RevenueExpense> getVehicleRevenueExpenseReportData(ReportReq reportReq);

    List<RevenueExpense> getCompanyRevenueExpenseReportData(ReportReq reportReq);

    Map<String, EstimateAndEarnedIncome> calculateContributorEstimatedAndEarnedIncome(ReportReq reportReq);

    ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncomeById(ReportReq reportReq);

    List<ContributorIncomesDetail> getContributorIncomesDetails(ReportReq reportReq);

    List<DriverIncomes> getDriversIncome(ReportReq reportReq);

    DriverIncomeRes getDriversIncomeById(ReportReq reportReq);
}
