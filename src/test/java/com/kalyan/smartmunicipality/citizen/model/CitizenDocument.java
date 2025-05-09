package com.kalyan.smartmunicipality.citizen.model;

import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CitizenDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long citizenId;
    private DocumentType documentType;
    private byte[] fileData;
    private String fileName;
    private LocalDate uploadDate;

}
