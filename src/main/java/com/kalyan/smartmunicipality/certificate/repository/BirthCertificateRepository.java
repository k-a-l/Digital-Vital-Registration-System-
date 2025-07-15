package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BirthCertificateRepository extends JpaRepository<BirthCertificateRequest,Long> {
    boolean existsByChildNameAndDateOfBirthAndCitizen(String childName, LocalDate dateOfBirth, Citizen citizen);

    List<BirthCertificateRequest> findByCitizenId(Long id);

    List<BirthCertificateRequest> findByStaffUserMunicipality(String municipality);


    List<BirthCertificateRequest> findByMunicipality(String municipality);
}
