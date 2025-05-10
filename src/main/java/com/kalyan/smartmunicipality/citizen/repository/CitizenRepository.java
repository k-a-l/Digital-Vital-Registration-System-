package com.kalyan.smartmunicipality.citizen.repository;

import com.kalyan.smartmunicipality.citizen.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long>{

}
