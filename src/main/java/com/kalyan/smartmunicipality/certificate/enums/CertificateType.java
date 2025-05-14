package com.kalyan.smartmunicipality.certificate.enums;

public enum CertificateType {
    BIRTH("Birth"),
    DEATH("Death"),
    MARRIAGE("Marriage") ;

    private final String label;
    CertificateType(String label) {
        this.label = label;
    }
}
