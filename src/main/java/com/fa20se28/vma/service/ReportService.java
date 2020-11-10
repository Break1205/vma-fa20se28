package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ReportReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException;
}
