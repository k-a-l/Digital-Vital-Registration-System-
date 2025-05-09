package com.kalyan.smartmunicipality.citizen.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHERS("Others");
    private final String label;
    Gender(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}
