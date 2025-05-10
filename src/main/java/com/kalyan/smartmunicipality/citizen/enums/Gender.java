package com.kalyan.smartmunicipality.citizen.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHERS("Others");
    private final String label;
    Gender(String label) {
        this.label = label;
    }

}
