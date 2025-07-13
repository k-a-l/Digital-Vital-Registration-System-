package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateFileRepository extends JpaRepository<CertificateFile,Long> {
    CertificateFile findByReferenceNumber(String reference);
    Optional<CertificateFile> findByBirthCertificateRequestId(Long requestId);

    Optional<CertificateFile> findByCitizen_Id(Long id);

    Optional<CertificateFile> findByDeathCertificateRequestId(long id);

    Optional<CertificateFile> findByMarriageCertificateRequestId(long id);
}
