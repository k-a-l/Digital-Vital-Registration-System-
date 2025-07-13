package com.kalyan.smartmunicipality.citizen.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    CITIZENSHIP("Citizenship Card"),
    VOTER_ID("Voter ID Card"),
    NATIONAL_ID("National ID"),
    LICENCE("License"),
    DEATH("Death"),
    BIRTH("Birth"),
    MARRIAGE("Marriage"),
    PASSPORT("Passport");

    private final String label;

    DocumentType(String label) {
        this.label = label;
    }

}

