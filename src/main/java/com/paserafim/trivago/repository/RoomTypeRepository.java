package com.paserafim.trivago.repository;

import com.paserafim.trivago.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    List<RoomType> findByRoomTypeIdOrderByAmount(Long roomTypeId);
}
