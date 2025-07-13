package com.kalyan.smartmunicipality.dashboard;

import com.kalyan.smartmunicipality.certificate.service.BirthCertificateService;
import com.kalyan.smartmunicipality.certificate.service.DeathCertificateRequestService;
import com.kalyan.smartmunicipality.marriage.service.MarriageCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardAnalysis {
    private final DeathCertificateRequestService deathCertificateRequestService;
    private final BirthCertificateService birthCertificateService;
    private final MarriageCertificateRequestService marriageCertificateRequestService;

    @GetMapping("/birth/count-by-month")
    public ResponseEntity<Map<String, Long>> getBirthCountByMonth() {
        return ResponseEntity.ok().body(birthCertificateService.countBirthByMonth());
    }

    @GetMapping("/death/count-by-month")
    public ResponseEntity<Map<String, Long>> getDeathCountByMonth() {
        return ResponseEntity.ok().body(deathCertificateRequestService.countDeathByMonth());
    }

    @GetMapping("/marriage/count-by-month")
    public ResponseEntity<Map<String, Long>> getMarriageCountByMonth() {
        return ResponseEntity.ok().body(marriageCertificateRequestService.countMarriageByMonth());
    }

}
