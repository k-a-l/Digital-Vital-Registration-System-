package com.kalyan.smartmunicipality.certificate.repository;

import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import jakarta.validation.constraints.Past;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeathCertificateRepository extends JpaRepository<DeathCertificateRequest, Long> {
    boolean existsByDeceasedNameAndDateOfBirthAndDeceasedDateAndRequestedBy(
            String deceasedName,
            LocalDate dateOfBirth,
            LocalDate deceasedDate,
            Citizen requestedBy
    );

    List<DeathCertificateRequest> findByRequestedById(Long id);
}
