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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        // create new Reservation
        Reservation newReservation = new Reservation();
        //newReservation.setReservationId(55L);
        newReservation.setTotalAmount(0.0);
        newReservation.setCustomer(customer);
        // Set random string code with 6 alphanumeric characters
        String reference = RandomStringUtils.random(6, true,true).toUpperCase();
        newReservation.setReference(reference);
        //newReservation.setRoomTypes(new HashSet<RoomType>(roomTypesToBook));
        //reservationRepository.save(newReservation);

        List<ReservationTypeRoom> reservationTypeRoomList = new ArrayList<ReservationTypeRoom>();

        // Create Reservation_Type_Room
        for (RoomType roomType : roomTypesToBook) {
            Optional<RoomType> rt = roomTypeRepository.findAll().stream().filter(roomT -> roomT.getRoomTypeCode().equals(roomType.getRoomTypeCode())).findAny();

            if (rt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            else {
                roomType.setRoomTypeId(rt.get().getRoomTypeId());
                roomType.setRoomTypeName(rt.get().getRoomTypeName());
                roomType.setAmount(rt.get().getAmount());
                roomType.setRoomsAvailable(rt.get().getRoomsAvailable());
                // calculate totalAmount
                totalAmount += roomType.getAmount();

                ReservationTypeRoomId reservationTypeRoomId = new ReservationTypeRoomId(newReservation.getReservationId(),roomType.getRoomTypeId());
                ReservationTypeRoom newReservationTypeRoom = new ReservationTypeRoom();
                newReservationTypeRoom.setReservationTypeRoomId(reservationTypeRoomId);
                newReservationTypeRoom.setStartDate(startDate);
                newReservationTypeRoom.setEndDate(endDate);
                newReservationTypeRoom.setQuantity(1);

                reservationTypeRoomList.add(newReservationTypeRoom);
            }
        }

        newReservation.setRoomTypes(new HashSet<RoomType>(roomTypesToBook));
        Reservation r = reservationRepository.save(newReservation);

        // save reservationTypeRooms
        for (ReservationTypeRoom reservationTypeRoom : reservationTypeRoomList){
            reservationTypeRoom.getReservationTypeRoomId().setReservationId(r.getReservationId());
            reservationTypeRoomRepository.save(reservationTypeRoom);
        }

        // Update totalAmount and Reservation
        Optional<Reservation> reservation = reservationRepository.findById(newReservation.getReservationId());
        if (reservation.isPresent()) {
            reservation.get().setTotalAmount(totalAmount);
            reservationRepository.save(reservation.get());
        }

        // Create the ReservationResponseModel object
        ReservationResponseModel reservationResponseModel = new ReservationResponseModel();
        reservationResponseModel.setStartDate(startDate);
        reservationResponseModel.setEndDate(endDate);
        reservationResponseModel.setCustomerFullName(customerFullName);
        reservationResponseModel.setCustomerEmail(customerMail);
        reservationResponseModel.setRoomTypes(roomTypesToBook);
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
            // filter TypeRooms from reservation
            List<ReservationTypeRoom> filteredReservationTypeRoomList = reservationTypeRoomList.stream()
                    .filter(reservationTypeRoom -> reservationTypeRoom.getReservationTypeRoomId().getReservationId().equals(reservation.getReservationId())).collect(Collectors.toList());
            //Get customer from reservation
            Optional<Customer> customer = customerRepository.findById(reservation.getCustomer().getCustomerId());
            //Filter roomTypes by Id
            List<RoomType> roomTypesList = filteredReservationTypeRoomList.stream().map(reservationTypeRoom -> {
                Optional<RoomType> roomType = roomTypeRepository.findById(reservationTypeRoom.getReservationTypeRoomId().getRoomTypeId());
                return roomType.get();
            }).collect(Collectors.toList());

            ReservationResponseModel reservationResponseModel = new ReservationResponseModel();
            reservationResponseModel.setStartDate(filteredReservationTypeRoomList.get(0).getStartDate());
            reservationResponseModel.setEndDate(filteredReservationTypeRoomList.get(0).getEndDate());
            reservationResponseModel.setReference(reference);
            reservationResponseModel.setTotalAmount(reservation.getTotalAmount());
            reservationResponseModel.setCustomerFullName(customer.get().getFullName());
            reservationResponseModel.setCustomerEmail(customer.get().getEmail());

            reservationResponseModel.setRoomTypes(roomTypesList);

            return ResponseEntity.ok(reservationResponseModel);
        }
    }

}
