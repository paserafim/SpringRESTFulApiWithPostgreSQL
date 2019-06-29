package com.paserafim.trivago.repository;

import com.paserafim.trivago.model.Availabitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availabitity, Long> {

}
