package com.paserafim.trivago.controller;

import com.paserafim.trivago.model.*;
import com.paserafim.trivago.repository.AvailabilityRepository;
import com.paserafim.trivago.repository.RoomTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AvailabilityController {
    Logger logger = LoggerFactory.getLogger(AvailabilityController.class);

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    private RoomAvailabilityResponseModel roomAvailabilityResponseModel = new RoomAvailabilityResponseModel();

    public AvailabilityController(AvailabilityRepository availabilityRepository, RoomTypeRepository roomTypeRepository) {
        this.availabilityRepository = availabilityRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    /*@GetMapping("/availability")
    public List<Availabitity> getAvailabilities(){
        return availabilityRepository.findAll();
    }*/

    @PostMapping("/availability")
    public ResponseEntity<RoomAvailabilityResponseModel> getRoomAvailability(@Valid @RequestBody AvailabilityRequestModel roomAvailability ) {

        // Extract startDate, endDate and occupancies from Body
        LocalDate startDate = roomAvailability.getStartDate();
        LocalDate endDate = roomAvailability.getEndDate();
        List<Occupancy> occupancies = roomAvailability.getOccupancy();

        List<RoomType> availableRooms = new ArrayList<RoomType>();
        List<RoomType> roomsAvailableWithOccupancy = new ArrayList<RoomType>();

        for (Availabitity availabitity : this.availabilityRepository.findAll()) {
            RoomType roomType = new RoomType();
            LocalDate startAvailableDate = Instant.ofEpochMilli(availabitity.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endAvailableDate = Instant.ofEpochMilli(availabitity.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if ( (startAvailableDate.isEqual(startDate) || startAvailableDate.isBefore(startDate)) && (endAvailableDate.isEqual(endDate) || endAvailableDate.isAfter(endDate)) ) {
                roomType = availabitity.getRoomType();
                availableRooms.add(roomType);
            }
        }

        logger.info("startdate ==> " + startDate); logger.info("enddate ==> " + endDate);

        if (availableRooms.size() > 0) {
            if (occupancies != null) {
                for (Occupancy occ : occupancies)
                    logger.info("occupancy [ adults: " + occ.getAdults().toString() + ", juniors:  " + occ.getJuniors().toString() + ", babies: " + occ.getBabies().toString() + " ]");

                // filter by occupancies
                roomsAvailableWithOccupancy = availableRooms.stream().
                        filter(room -> occupancies.stream()
                                .anyMatch(occupancy ->
                                        occupancy.getAdults().equals(room.getOccupancy().getAdults()) &&
                                                occupancy.getJuniors().equals(room.getOccupancy().getJuniors()) &&
                                                occupancy.getBabies().equals(room.getOccupancy().getBabies())
                                )).collect(Collectors.toList());

                if (roomsAvailableWithOccupancy.size() > 0) this.roomAvailabilityResponseModel.setRoomTypes(roomsAvailableWithOccupancy);
                else this.roomAvailabilityResponseModel.setRoomTypes(availableRooms);

            } else {
                // standard occupancy
                this.roomAvailabilityResponseModel.setRoomTypes(availableRooms.stream()
                        .filter(r -> r.getRoomTypeCode().equals("STD")).collect(Collectors.toList()));
            }
            this.roomAvailabilityResponseModel.setStartDate(startDate);
            this.roomAvailabilityResponseModel.setEndDate(endDate);

            return ResponseEntity.ok(this.roomAvailabilityResponseModel);
         } else
            return ResponseEntity.notFound().build();
    }
}