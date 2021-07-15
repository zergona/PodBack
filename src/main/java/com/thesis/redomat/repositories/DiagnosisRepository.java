package com.thesis.redomat.repositories;

import com.thesis.redomat.data.models.Diagnosis;
import org.springframework.data.repository.CrudRepository;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {
}
