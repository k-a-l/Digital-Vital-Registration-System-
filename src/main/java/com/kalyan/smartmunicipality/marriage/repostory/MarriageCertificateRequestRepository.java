package com.kalyan.smartmunicipality.marriage.repostory;

import com.kalyan.smartmunicipality.marriage.dto.MarriageCertificateResponseDto;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageCertificateRequestRepository extends JpaRepository<MarriageCertificateRequest, Long> {
    boolean existsByRequestedById(Long id);

    Optional<MarriageCertificateRequest>findByRequestedById(Long id);


    Optional<MarriageCertificateRequest> findByMunicipality(String municipality);
}
