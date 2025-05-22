package com.kalyan.smartmunicipality.signature.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SignatureKeys implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, length = 2048)
    private String privateKey;

    @Lob
    @Column(nullable = false, length = 2048)
    private String publicKey;

    @Lob
    @Column(nullable = false, length = 2048)
    private String modulus;
}
