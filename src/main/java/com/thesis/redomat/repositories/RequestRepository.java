package com.thesis.redomat.repositories;

import com.thesis.redomat.data.models.Patient;
import com.thesis.redomat.data.models.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RequestRepository extends CrudRepository <Request, Integer> {

    @Query(value = "SELECT COUNT (request_Id) FROM request WHERE date_trunc('day', date) = ?1 AND status = 1", nativeQuery = true)
    public Optional<Integer> countTodayActive(@Param("date") LocalDateTime date);

    @Query(value = "SELECT * FROM request WHERE date_trunc('day', date) = ?1 AND status = 1", nativeQuery = true)
    public Iterable<Request> findTodayActive(@Param("date") LocalDateTime date);
}
