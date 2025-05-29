package com.kalyan.smartmunicipality.citizen.repository;


import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument,Long> {
    List<CitizenDocument> findByCitizen_Id(Long citizenId);
}
