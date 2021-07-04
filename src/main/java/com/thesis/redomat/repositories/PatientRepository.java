package com.thesis.redomat.repositories;

import com.thesis.redomat.data.models.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    @Query(value = "SELECT * FROM patient WHERE ssn = ?1", nativeQuery = true)
    public Optional<Patient> findBySsn(@Param("ssn") String ssn);

}
