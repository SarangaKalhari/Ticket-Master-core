package edu.icet.controller;

import edu.icet.model.dto.seats.SeatHoldRequestDTO;
import edu.icet.model.dto.seats.SeatHoldResponseDTO;
import edu.icet.model.entity.Seat;
import edu.icet.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public String seats(){
        return "Seats ..";
    }

    @PostMapping("/book-seat")
    public ResponseEntity<String> bookSeat(@RequestBody SeatHoldRequestDTO requestDTO) {
        seatService.bookingSeat(requestDTO);
        return ResponseEntity.ok("Seat successfully held for 10 minutes");
    }

    @GetMapping("/check-available/{eventId}")
    public List<SeatHoldResponseDTO> getAvailableSeats(@PathVariable long eventId){
        return seatService.getAvailableSeats(eventId);
    }

}
