package com.kalyan.smartmunicipality.citizen.enums;

public enum DocumentType {
    CITIZENSHIP("Citizenship Card"),
    VOTER_ID("Voter ID Card"),
    NATIONAL_ID("National ID"),
    LICENCE("License"),
    PASSPORT("Passport");

    private final String label;

    DocumentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

