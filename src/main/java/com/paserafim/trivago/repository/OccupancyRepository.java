package com.paserafim.trivago.repository;

import com.paserafim.trivago.model.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {
}
