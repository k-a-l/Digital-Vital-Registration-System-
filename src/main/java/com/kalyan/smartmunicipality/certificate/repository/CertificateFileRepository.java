package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateFileRepository extends JpaRepository<CertificateFile,Long> {
    CertificateFile findByReferenceNumber(String reference);
}
