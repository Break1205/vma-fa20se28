package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;
import com.fa20se28.vma.response.ContributorIncomeSummaryMonthRes;
import com.fa20se28.vma.response.ContributorIncomeSummaryYearRes;
import com.fa20se28.vma.response.DriverIncomeSummaryMonthRes;
import com.fa20se28.vma.response.DriverIncomeSummaryYearRes;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
import com.fa20se28.vma.response.DriverIncomeRes;
import com.fa20se28.vma.response.DriversIncomeRes;
import com.fa20se28.vma.response.MaintenanceReportRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;
import com.fa20se28.vma.response.RevenueExpenseSummaryMonthRes;
import com.fa20se28.vma.response.RevenueExpenseSummaryYearRes;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportComponent reportComponent;
    private final List<Quarter> monthsInYear = Arrays.asList(
            Quarter.JANUARY, Quarter.FEBRUARY, Quarter.MARCH,
            Quarter.APRIL, Quarter.MAY, Quarter.JUNE,
            Quarter.JULY, Quarter.AUGUST, Quarter.SEPTEMBER,
            Quarter.OCTOBER, Quarter.NOVEMBER, Quarter.DECEMBER);

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
    public ScheduleRes getScheduleReportDate(ReportReq reportReq) {
        ScheduleRes scheduleRes = new ScheduleRes();
        scheduleRes.setSchedules(reportComponent.getScheduleReportData(reportReq));
        return scheduleRes;
    }

    @Override
    public VehicleReportRes getVehicleReportData(ReportReq reportReq) {
        VehicleReportRes vehicleReportRes = new VehicleReportRes();
        vehicleReportRes.setVehicleReports(reportComponent.getVehicleReportData(reportReq));
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
    public RevenueExpenseReportRes getRevenueExpenseReportData(ReportReq reportReq) {
        RevenueExpenseReportRes revenueExpenseReportRes = new RevenueExpenseReportRes();
        if (reportReq.getVehicleId() != null) {
            revenueExpenseReportRes.setRevenueExpenses(reportComponent.getVehicleRevenueExpenseReportData(reportReq));
        } else {
            revenueExpenseReportRes.setRevenueExpenses(reportComponent.getCompanyRevenueExpenseReportData(reportReq));
        }
        return revenueExpenseReportRes;
    }

    @Override
    public ContributorIncomeRes getContributorsIncomesReportData(ReportReq reportReq) {
        ContributorIncomeRes contributorIncomeRes = new ContributorIncomeRes();
        contributorIncomeRes.setContributorEstimateAndEarnedIncomes(reportComponent.calculateContributorEstimatedAndEarnedIncome(reportReq));
        return contributorIncomeRes;
    }

    @Override
    public ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncome(ReportReq reportReq) {
        return reportComponent.getContributorEarnedAndEstimatedIncomeById(reportReq);
    }

    @Override
    public ContributorIncomeSummaryYearRes getContributorIncomeSummary(ReportReq reportReq) {
        ContributorIncomeSummaryYearRes contributorIncomeSummaryYearRes = new ContributorIncomeSummaryYearRes();

        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? reportReq.getYear() : currentDate.getYear();
        contributorIncomeSummaryYearRes.setYear(year);
        List<ContributorIncomeSummaryMonthRes> contributorIncomeSummaryMonthResList = new ArrayList<>();

        float totalEstimated = 0;
        float totalEarned = 0;
        for (Quarter quarter : monthsInYear) {
            ContributorIncomeSummaryMonthRes contributorIncomeSummaryMonthRes = new ContributorIncomeSummaryMonthRes();
            reportReq.setQuarter(quarter);
            contributorIncomeSummaryMonthRes.setQuarter(quarter);

            ContributorEarnedAndEstimatedIncome contributorEarnedAndEstimatedIncome = reportComponent.getContributorEarnedAndEstimatedIncomeById(reportReq);
            contributorIncomeSummaryMonthRes.setContributorEarnedAndEstimatedIncome(contributorEarnedAndEstimatedIncome);

            contributorIncomeSummaryMonthResList.add(contributorIncomeSummaryMonthRes);
            totalEstimated += contributorEarnedAndEstimatedIncome.getEstimated();
            totalEarned += contributorEarnedAndEstimatedIncome.getEarned();
        }

        contributorIncomeSummaryYearRes.setContributorIncomeSummaryMonthResList(contributorIncomeSummaryMonthResList);
        contributorIncomeSummaryYearRes.setTotalEarned(totalEarned);
        contributorIncomeSummaryYearRes.setTotalEstimated(totalEstimated);
        return contributorIncomeSummaryYearRes;
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

    @Override
    public DriverIncomeSummaryYearRes getDriverIncomeSummary(ReportReq reportReq) {
        DriverIncomeSummaryYearRes driverIncomeSummaryYearRes = new DriverIncomeSummaryYearRes();

        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? reportReq.getYear() : currentDate.getYear();
        driverIncomeSummaryYearRes.setYear(year);

        List<DriverIncomeSummaryMonthRes> driverIncomeSummaryMonthResList = new ArrayList<>();
        float totalEarned = 0;
        for (Quarter quarter : monthsInYear) {
            DriverIncomeSummaryMonthRes driverIncomeSummaryMonthRes = new DriverIncomeSummaryMonthRes();
            reportReq.setQuarter(quarter);
            driverIncomeSummaryMonthRes.setQuarter(quarter);

            DriverIncomeRes driverIncomeRes = reportComponent.getDriversIncomeById(reportReq);
            driverIncomeSummaryMonthRes.setDriverIncomeRes(driverIncomeRes);

            driverIncomeSummaryMonthResList.add(driverIncomeSummaryMonthRes);
            totalEarned += driverIncomeRes.getEarnedValue();
        }
        driverIncomeSummaryYearRes.setDriverIncomeSummaryMonthResList(driverIncomeSummaryMonthResList);
        driverIncomeSummaryYearRes.setTotalEarned(totalEarned);
        return driverIncomeSummaryYearRes;
    }

    @Override
    public RevenueExpenseSummaryYearRes getRevenueExpenseSummaryReportData(ReportReq reportReq) {
        RevenueExpenseSummaryYearRes revenueExpenseSummaryYearRes = new RevenueExpenseSummaryYearRes();
        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? reportReq.getYear() : currentDate.getYear();
        revenueExpenseSummaryYearRes.setYear(year);

        List<RevenueExpenseSummaryMonthRes> revenueExpenseSummaryMonthResList = new ArrayList<>();
        float totalRevenue = 0;
        float totalExpense = 0;
        for (Quarter quarter : monthsInYear) {
            RevenueExpenseSummaryMonthRes revenueExpenseSummaryMonthRes = new RevenueExpenseSummaryMonthRes();
            reportReq.setQuarter(quarter);
            revenueExpenseSummaryMonthRes.setQuarter(quarter);

            RevenueExpenseReportRes revenueExpenseReportRes = reportComponent.getRevenueExpenseDetailReportData(reportReq);
            revenueExpenseSummaryMonthRes.setRevenueExpenseReportRes(revenueExpenseReportRes);

            revenueExpenseSummaryMonthResList.add(revenueExpenseSummaryMonthRes);
            totalRevenue += revenueExpenseReportRes.getTotalRevenue();
            totalExpense += revenueExpenseReportRes.getTotalExpense();
        }
        revenueExpenseSummaryYearRes.setRevenueExpenseSummaryMonthResList(revenueExpenseSummaryMonthResList);
        revenueExpenseSummaryYearRes.setTotalRevenue(totalRevenue);
        revenueExpenseSummaryYearRes.setTotalExpense(totalExpense);
        return revenueExpenseSummaryYearRes;
    }
}
