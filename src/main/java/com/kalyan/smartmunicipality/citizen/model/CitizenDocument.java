package com.kalyan.smartmunicipality.citizen.model;

import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CitizenDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Many document belong to one citizen
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id",nullable = false)
    private Citizen citizenId;

    private DocumentType documentType;
    @Lob
    private byte[] fileData;
    private String fileName;
    private LocalDate uploadDate;
    private Long verifiedBy;
    private LocalDate verifiedDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
