package edu.icet.controller;

import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String booking(){
        return "Booking ..";
    }

    @PostMapping("/get-booking")
    public String getBooking(@RequestBody BookingRequestDTO requestDTO ){
        return bookingService.getBooking(requestDTO);
    }
}
