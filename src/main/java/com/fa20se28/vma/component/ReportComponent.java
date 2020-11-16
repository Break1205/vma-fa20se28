package com.fa20se28.vma.component;

import com.fa20se28.vma.model.ByteArrayInputStreamWrapper;
import com.fa20se28.vma.request.ReportReq;

import java.io.IOException;

public interface ReportComponent {
    ByteArrayInputStreamWrapper exportReportByType(ReportReq reportReq) throws IOException;
}
