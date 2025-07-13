package com.kalyan.smartmunicipality.marriage.repostory;

import com.kalyan.smartmunicipality.marriage.model.ForeignPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeignPersonRepository extends JpaRepository<ForeignPerson, Long> {
}
