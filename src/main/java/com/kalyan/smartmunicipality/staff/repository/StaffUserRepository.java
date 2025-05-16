package com.kalyan.smartmunicipality.staff.repository;

import com.kalyan.smartmunicipality.staff.model.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffUserRepository extends JpaRepository<StaffUser, Long> {
}
