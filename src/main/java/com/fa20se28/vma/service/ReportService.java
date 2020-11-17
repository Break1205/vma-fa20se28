package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ReportReq;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
    ResponseEntity<InputStreamResource> exportReportByType(ReportReq reportReq) throws IOException;
}
