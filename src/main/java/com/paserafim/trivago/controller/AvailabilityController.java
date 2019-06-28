package com.paserafim.trivago.controller;

import com.paserafim.trivago.model.Occupancy;
import com.paserafim.trivago.model.RoomAvailabilityResponseModel;
import com.paserafim.trivago.model.RoomType;
import com.paserafim.trivago.repository.AvailabilityRepository;
import com.paserafim.trivago.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AvailabilityController {
    @Autowired
    private AvailabilityRepository AvailabilityRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    private RoomAvailabilityResponseModel roomAvailabilityRequestModel= new RoomAvailabilityResponseModel();

    public AvailabilityController(AvailabilityRepository availabilityRepository,
                                  RoomTypeRepository roomTypeRepository) {
        AvailabilityRepository = availabilityRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @PostMapping("/availability")
    public List<RoomAvailabilityResponseModel> getRoomAvailability(@Valid @RequestBody RoomAvailabilityResponseModel roomAvailability ) {
        roomAvailabilityRequestModel.setStartDate(LocalDate.now());
        roomAvailabilityRequestModel.setEndDate(LocalDate.now().plusDays(10));
        RoomType roomType = new RoomType();
        Occupancy occupancy = new Occupancy();
        occupancy.setAdults(3);
        occupancy.setBabies(0);
        occupancy.setJuniors(0);
        roomType.setAmount(150.5);
        roomType.setRoomTypeCode("TRL");
        roomType.setRoomTypeName("Triple");
        roomType.setOccupancy(occupancy);
        roomType.setRoomsAvailable(3);
        ArrayList<RoomType> roomList = new ArrayList<RoomType>();
        roomList.add(roomType);
        roomAvailabilityRequestModel.setRoomTypes(roomList);
        List<RoomAvailabilityResponseModel> response = new ArrayList<RoomAvailabilityResponseModel>();
        response.add(roomAvailabilityRequestModel);
        return response;
    }
}