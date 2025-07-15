package com.kalyan.smartmunicipality.certificate.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum CertificateStatus {
    PENDING("Pending"),
    PENDING_VIDEO_CALL_VERIFICATION("Pending Video Call Verification"),
    APPROVED_BY_VERIFIER("Approved by Verifier"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String label;
    CertificateStatus(String label) {
        this.label = label;
    }
}
