package com.kalyan.smartmunicipality.citizen.repository;

import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long>{
    @Query("SELECT COUNT(c) FROM Citizen c WHERE c.gender = :gender")
    Long countByGender(@Param("gender") Gender gender);

}
