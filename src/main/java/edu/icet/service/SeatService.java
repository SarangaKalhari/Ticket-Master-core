package edu.icet.service;

import edu.icet.model.dto.seats.SeatHoldRequestDTO;
import edu.icet.model.entity.Seat;
import edu.icet.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public void bookingSeat(SeatHoldRequestDTO requestDTO) {

        seatRepository.expireUnpaidHolds();

        Seat seat = seatRepository.searchSeat(requestDTO.getSeat_number());

        if (seat == null) {
            throw new RuntimeException("Seat not found");
        }

        if (!"AVAILABLE".equals(seat.getStatus())) {
            throw new RuntimeException("Seat is not available");
        }

        seat.setStatus("HELD");
        seat.setHeldByUserId(requestDTO.getUserId());
        seat.setHoldExpiry(LocalDateTime.now().plusMinutes(10));

        seatRepository.bookSeat(seat);
    }

}
