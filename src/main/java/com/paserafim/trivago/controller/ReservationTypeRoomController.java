package com.paserafim.trivago.controller;

import com.paserafim.trivago.repository.CustomerRepository;
import com.paserafim.trivago.repository.ReservationRepository;
import com.paserafim.trivago.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationTypeRoomController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

//    public ReservationTypeRoomController(CustomerRepository customerRepository, ReservationRepository reservationRepository, RoomTypeRepository roomTypeRepository) {
//        this.customerRepository = customerRepository;
//        this.reservationRepository = reservationRepository;
//        this.roomTypeRepository = roomTypeRepository;
//    }

    /*@PostMapping("/reservations")
    public Reservation createQuestion(@Valid @RequestBody Question question){
        return questionRepository.save(question);
    }*/


}

