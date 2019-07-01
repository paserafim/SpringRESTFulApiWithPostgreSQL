package com.paserafim.trivago.repository;

import com.paserafim.trivago.model.ReservationTypeRoom;
import com.paserafim.trivago.model.ReservationTypeRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationTypeRoomRepository extends JpaRepository<ReservationTypeRoom, ReservationTypeRoomId> {
}
