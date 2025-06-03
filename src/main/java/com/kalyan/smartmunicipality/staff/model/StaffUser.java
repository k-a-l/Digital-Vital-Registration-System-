package com.kalyan.smartmunicipality.staff.model;

import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.enums.Status;
import com.kalyan.smartmunicipality.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private String department;

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE or INACTIVE

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "added_by_id")
    private StaffUser addedBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staffUser")
    private User user;

}
