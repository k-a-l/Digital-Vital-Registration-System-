package com.kalyan.smartmunicipality.signature.repository;

import com.kalyan.smartmunicipality.signature.model.SignatureKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignatureKeysRepository extends JpaRepository<SignatureKeys, Long> {
    Optional<SignatureKeys> findTopByOrderByIdDesc();
}
