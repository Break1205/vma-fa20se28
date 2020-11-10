package com.fa20se28.vma.component;

import com.fa20se28.vma.request.ReportReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportComponent {
    void exportReportByType(HttpServletResponse response, ReportReq reportReq) throws IOException;

}
