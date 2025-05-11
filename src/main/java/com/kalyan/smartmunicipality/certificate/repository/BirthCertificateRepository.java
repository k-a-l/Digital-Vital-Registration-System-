package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.model.BirthCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthCertificateRepository extends JpaRepository<BirthCertificate,Long> {
}
