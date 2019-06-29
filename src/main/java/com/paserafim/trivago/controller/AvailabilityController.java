package com.paserafim.trivago.controller;

import com.paserafim.trivago.model.*;
import com.paserafim.trivago.repository.AvailabilityRepository;
import com.paserafim.trivago.repository.RoomTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/availability")
    public List<Availabitity> getAvailabilities(){
        return availabilityRepository.findAll();
    }

    @PostMapping("/availability")
    public ResponseEntity<RoomAvailabilityResponseModel> getRoomAvailability(@Valid @RequestBody AvailabilityRequestModel roomAvailability ) {

        // Extract startDate, endDate and occupancies from Body
        LocalDate startDate = roomAvailability.getStartDate();
        LocalDate endDate = roomAvailability.getEndDate();
        List<Occupancy> occupancies = roomAvailability.getOccupancy();

        logger.info("startdate ==> " + startDate);
        logger.info("enddate ==> " + endDate);
        if (occupancies != null)
            for (Occupancy occ : occupancies)
                logger.info("occupancy [ adults: " + occ.getAdults().toString() + ", juniors:  " + occ.getJuniors().toString() + ", babies: " + occ.getBabies().toString() + " ]");

        List<RoomType> availableRooms = new ArrayList<RoomType>();

        for (Availabitity availabitity : this.availabilityRepository.findAll()) {
            RoomType roomType = new RoomType();
            LocalDate startAvailableDate = Instant.ofEpochMilli(availabitity.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endAvailableDate = Instant.ofEpochMilli(availabitity.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (startAvailableDate.isBefore(startDate) && endAvailableDate.isAfter(endDate)) {
                roomType = availabitity.getRoomType();
                availableRooms.add(roomType);
            }
        }

        if (availableRooms.size() > 0) {
            for(RoomType roomType : availableRooms) {
                logger.info("RoomType loaded [ " + roomType.toString() + " ]");
            }

            this.roomAvailabilityResponseModel.setStartDate(startDate);
            this.roomAvailabilityResponseModel.setEndDate(endDate);
            this.roomAvailabilityResponseModel.setRoomTypes(availableRooms);
            return ResponseEntity.ok(this.roomAvailabilityResponseModel);
        } else
            return ResponseEntity.notFound().build();
    }
}