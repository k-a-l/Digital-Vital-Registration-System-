package com.kalyan.smartmunicipality.user.model;

import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.staff.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // Optional: better naming in DB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true) // Nullable in OTP login, used later if password-based login is added
    private String password;

    @Column(length = 500)
    private String jwtToken;

    private Role role;

    @OneToOne
    @JoinColumn(name = "citizen_id", referencedColumnName = "id")
    private Citizen citizen;

    private LocalDateTime createdAt;
}
