package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.configuration.ExcelUtil;
import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.mapper.ReportMapper;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
import com.fa20se28.vma.response.MaintenanceReportRes;
import com.fa20se28.vma.response.RevenueExpenseReportRes;
import com.fa20se28.vma.response.ScheduleRes;
import com.fa20se28.vma.response.VehicleReportRes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportComponentImpl implements ReportComponent {
    private final ReportMapper reportMapper;

    public ReportComponentImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    @Override
    public ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException {
        ExcelUtil excelUtil = new ExcelUtil(getFirstAndLastDayInAMonth(reportReq), reportMapper);
        return excelUtil.export(reportReq);
    }

    @Override
    public ScheduleRes exportScheduleReportData(ReportReq reportReq) {
        ScheduleRes scheduleRes = new ScheduleRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        scheduleRes.setSchedules(
                reportMapper.getListSchedule(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString()));
        return scheduleRes;
    }

    @Override
    public VehicleReportRes exportVehicleReportData(ReportReq reportReq) {
        VehicleReportRes vehicleReportRes = new VehicleReportRes();
        vehicleReportRes.setVehicleReports(reportMapper.getVehiclesForReport());
        return vehicleReportRes;
    }

    @Override
    public MaintenanceReportRes exportMaintenanceReportData(ReportReq reportReq) {
        MaintenanceReportRes maintenanceReportRes = new MaintenanceReportRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        maintenanceReportRes.setMaintenanceReports(
                reportMapper.getMaintenanceByVehicleIdForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId()));
        return maintenanceReportRes;
    }

    @Override
    public ContractReportRes exportContractsReportData(ReportReq reportReq) {
        ContractReportRes contractReportRes = new ContractReportRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        contractReportRes.setContractReports(
                reportMapper.getContractsReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString()));
        return contractReportRes;
    }

    @Override
    public RevenueExpenseReportRes exportVehicleRevenueExpenseReportData(ReportReq reportReq) {
        RevenueExpenseReportRes vehicleRevenueExpenseReportRes = new RevenueExpenseReportRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        vehicleRevenueExpenseReportRes.setRevenueExpenses(
                reportMapper.getVehicleRevenueExpenseForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString(),
                        reportReq.getVehicleId()));
        return vehicleRevenueExpenseReportRes;
    }

    @Override
    public RevenueExpenseReportRes exportCompanyRevenueExpenseReportData(ReportReq reportReq) {
        RevenueExpenseReportRes companyRevenueExpenseReportRes = new RevenueExpenseReportRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        companyRevenueExpenseReportRes.setRevenueExpenses(
                reportMapper.getCompanyRevenueExpenseForReport(
                        firstAndLast.get(0).toString(),
                        firstAndLast.get(1).toString()));
        return companyRevenueExpenseReportRes;
    }

    @Override
    public ContributorIncomeRes exportContributorIncomeReportData(ReportReq reportReq) {
        ContributorIncomeRes contributorIncomeRes = new ContributorIncomeRes();
        List<LocalDate> firstAndLast = getFirstAndLastDayInAMonth(reportReq);
        contributorIncomeRes.setContributorIncomes(reportMapper.getContributorIncomesForReport(firstAndLast.get(0).toString(),
                firstAndLast.get(1).toString(),
                reportReq.getVehicleId()));
        return contributorIncomeRes;
    }

    private List<LocalDate> getFirstAndLastDayInAMonth(ReportReq reportReq) {
        LocalDate currentDate = LocalDate.now();
        int year = reportReq.getYear() != null ? reportReq.getYear() : currentDate.getYear();
        Month firstMonthOfQuarter = currentDate.getMonth();
        Month lastMonthOfQuarter = currentDate.getMonth();
        if (reportReq.getQuarter() == null) {
            firstMonthOfQuarter = Month.JANUARY;
            lastMonthOfQuarter = Month.DECEMBER;
        } else if (reportReq.getQuarter() == Quarter.FIRST) {
            firstMonthOfQuarter = Month.JANUARY;
            lastMonthOfQuarter = Month.MARCH;
        } else if (reportReq.getQuarter() == Quarter.SECOND) {
            firstMonthOfQuarter = Month.APRIL;
            lastMonthOfQuarter = Month.JUNE;
        } else if (reportReq.getQuarter() == Quarter.THIRD) {
            firstMonthOfQuarter = Month.JULY;
            lastMonthOfQuarter = Month.SEPTEMBER;
        } else if (reportReq.getQuarter() == Quarter.FOURTH) {
            firstMonthOfQuarter = Month.OCTOBER;
            lastMonthOfQuarter = Month.DECEMBER;
        }
        LocalDate firstDayOfQuarter = LocalDate.of(year, firstMonthOfQuarter, 1);
        LocalDate lastDayOfQuarter = LocalDate.of(
                year,
                lastMonthOfQuarter,
                lastMonthOfQuarter.length(firstDayOfQuarter.isLeapYear()));
        List<LocalDate> firstAndLast = new ArrayList<>();
        firstAndLast.add(firstDayOfQuarter);
        firstAndLast.add(lastDayOfQuarter);
        return firstAndLast;
    }
}
