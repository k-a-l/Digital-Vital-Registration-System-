package com.kalyan.smartmunicipality.citizen.repository;


import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument,Long> {
}
