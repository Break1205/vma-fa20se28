package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.response.ContractReportRes;
import com.fa20se28.vma.response.ContributorIncomeRes;
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
    public ScheduleRes exportScheduleReportDate(ReportReq reportReq) {
        return reportComponent.exportScheduleReportData(reportReq);
    }

    @Override
    public VehicleReportRes exportVehicleReportData(ReportReq reportReq) {
        return reportComponent.exportVehicleReportData(reportReq);
    }

    @Override
    public MaintenanceReportRes exportMaintenanceReportData(ReportReq reportReq) {
        return reportComponent.exportMaintenanceReportData(reportReq);
    }

    @Override
    public ContractReportRes exportContractsReportData(ReportReq reportReq) {
        return reportComponent.exportContractsReportData(reportReq);
    }

    @Override
    public RevenueExpenseReportRes exportVehicleRevenueExpenseReportData(ReportReq reportReq) {
        return reportComponent.exportVehicleRevenueExpenseReportData(reportReq);
    }

    @Override
    public RevenueExpenseReportRes exportCompanyRevenueExpenseReportData(ReportReq reportReq) {
        return reportComponent.exportCompanyRevenueExpenseReportData(reportReq);
    }

    @Override
    public ContributorIncomeRes exportContributorIncomeReportData(ReportReq reportReq) {
        return reportComponent.exportContributorIncomeReportData(reportReq);
    }
}
