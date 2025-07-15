package com.kalyan.smartmunicipality.staff.model;

import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import com.kalyan.smartmunicipality.staff.enums.Department;
import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.enums.Status;
import com.kalyan.smartmunicipality.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StaffUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role; // ADMIN or SUPER_ADMIN

    private String designation;

    @Enumerated(value = EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE or INACTIVE

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String district;

    private String municipality;


    private String addedBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staffUser")
    private User user;

    @OneToMany(mappedBy = "staffUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BirthCertificateRequest> birthCertificateRequest;

    @OneToMany(mappedBy = "staffUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeathCertificateRequest> deathCertificateRequest;

    @OneToMany(mappedBy = "staffUser")
    private List<MarriageCertificateRequest> marriageCertificateRequest;
}
