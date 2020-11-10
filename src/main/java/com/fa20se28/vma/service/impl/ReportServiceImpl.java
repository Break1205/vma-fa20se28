package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ReportComponent;
import com.fa20se28.vma.request.ReportReq;
import com.fa20se28.vma.service.ReportService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportComponent reportComponent;


    public ReportServiceImpl(ReportComponent reportComponent) {
        this.reportComponent = reportComponent;
    }

    @Override
    public void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException {
        reportComponent.exportReportByType(response,reportReq);
    }
}
