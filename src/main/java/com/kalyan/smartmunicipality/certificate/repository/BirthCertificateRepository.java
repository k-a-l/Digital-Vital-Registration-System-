package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthCertificateRepository extends JpaRepository<BirthCertificateRequest,Long> {

}
