package com.paserafim.trivago.controller;

import com.paserafim.trivago.model.*;
import com.paserafim.trivago.repository.CustomerRepository;
import com.paserafim.trivago.repository.ReservationRepository;
import com.paserafim.trivago.repository.ReservationTypeRoomRepository;
import com.paserafim.trivago.repository.RoomTypeRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ReservationController {

    Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTypeRoomRepository reservationTypeRoomRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public ReservationController(ReservationRepository reservationRepository, ReservationTypeRoomRepository reservationTypeRoomRepository, CustomerRepository customerRepository, RoomTypeRepository roomTypeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTypeRoomRepository = reservationTypeRoomRepository;
        this.customerRepository = customerRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseModel> roomReservation(@Valid @RequestBody ReservationRequestModel reservationRequestModel){

        double totalAmount=0;

        // Extract startDate, endDate, customerFullName, customerMail and roomTypes from Body
        Date startDate = reservationRequestModel.getStartDate();
        Date endDate = reservationRequestModel.getEndDate();
        String customerFullName = reservationRequestModel.getCustomerFullName();
        String customerMail = reservationRequestModel.getCustomerMail();
        ArrayList<RoomType> roomTypesToBook = reservationRequestModel.getRoomTypes();

        // Create Reservation object
        Customer customer = customerRepository.findAll()
                .stream()
                .filter(c ->
                        c.getEmail().equals(customerMail) && c.getFullName().equals(customerFullName))
                .collect(Collectors.toList()).get(0);
        Reservation newReservation = new Reservation();
        newReservation.setCustomer(customer);

        String reference = RandomStringUtils.random(6, true,true).toUpperCase();

        newReservation.setReservationId(14L);
        newReservation.setReference(reference);
        newReservation.setTotalAmount(0.0);

        // Create Reservation_Type_Room

        for (RoomType roomType : roomTypesToBook) {
            //Long reservationId = reservationRepository.findAll().stream().filter(r->r.)
            ReservationTypeRoomId reservationTypeRoomId = new ReservationTypeRoomId(10L,1L);
            ReservationTypeRoom newReservationTypeRoom = new ReservationTypeRoom();
            newReservationTypeRoom.setReservationTypeRoomId(reservationTypeRoomId);
            newReservationTypeRoom.setStartDate(startDate);
            newReservationTypeRoom.setEndDate(endDate);
            newReservationTypeRoom.setQuantity(1);
            reservationTypeRoomRepository.save(newReservationTypeRoom);
            totalAmount += roomType.getAmount();
        }

        newReservation.setRoomTypes(new HashSet<RoomType>(roomTypesToBook));

        reservationRepository.save(newReservation);

        // Update totalAmount and Reservation
        Optional<Reservation> reservation = reservationRepository.findById(newReservation.getReservationId());
        if (reservation.isPresent()) {
            reservation.get().setTotalAmount(totalAmount);
            reservationRepository.save(reservation.get());
        }

        // Create the ReservationResponseModel object
        ReservationResponseModel reservationResponseModel = new ReservationResponseModel();
        reservationRequestModel.setStartDate(startDate);
        reservationRequestModel.setEndDate(endDate);
        reservationRequestModel.setCustomerFullName(customerFullName);
        reservationRequestModel.setCustomerMail(customerMail);
        reservationRequestModel.setRoomTypes(roomTypesToBook);
        reservationResponseModel.setTotalAmount(totalAmount);
        reservationResponseModel.setReference(newReservation.getReference());

        return ResponseEntity.ok(reservationResponseModel);
    }

    @PostMapping("/verify_reservation")
    public ResponseEntity<ReservationResponseModel> verifyReservation(@Valid @RequestBody ReservationVerificationRequestModel reservationVerificationRequestModel){
        // Get the reservation by Reference
        String reference = reservationVerificationRequestModel.getReference().toUpperCase();
        Reservation reservation = reservationRepository.findAll()
                .stream()
                .filter(c -> c.getReference().equals(reference))
                .collect(Collectors.toList()).get(0);

        if (reservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<ReservationTypeRoom> reservationTypeRoomList = reservationTypeRoomRepository.findAll();

            reservationTypeRoomList.stream()
                .filter( reservationTypeRoom ->
                        reservationTypeRoom.getReservationTypeRoomId().getReservationId().equals(reservation.getReservationId()));

            Optional<Customer> customer = customerRepository.findById(reservation.getCustomer().getCustomerId());

            ReservationResponseModel reservationResponseModel = new ReservationResponseModel();
            reservationResponseModel.setStartDate(reservationResponseModel.getStartDate());
            reservationResponseModel.setEndDate(reservationResponseModel.getEndDate());
            reservationResponseModel.setReference(reference);
            reservationResponseModel.setTotalAmount(reservation.getTotalAmount());
            reservationResponseModel.setCustomerFullName(customer.get().getFullName());
            reservationResponseModel.setCustomerEmail(customer.get().getEmail());

            List<RoomType> roomTypesList = new ArrayList<RoomType>();
            reservationTypeRoomList.stream().map(reservationTypeRoom -> {
                Optional<RoomType> roomType = roomTypeRepository.findById(reservationTypeRoom.getReservationTypeRoomId().getRoomTypeId());
                roomTypesList.add(roomType.get());
                return roomTypesList;
            });

            reservationResponseModel.setRoomTypes(roomTypesList);

            return ResponseEntity.ok(reservationResponseModel);
        }
    }

}
