package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.service.ReportService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=REPORT_" + reportReq.getReportType() + "_" + currentDateTime + ".xls";
        response.setHeader(headerKey, headerValue);
        reportComponent.exportReportByType(response, reportReq);
    }
}
