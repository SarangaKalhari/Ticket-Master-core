package edu.icet.service;

import edu.icet.model.dto.seats.SeatHoldRequestDTO;
import edu.icet.model.dto.seats.SeatHoldResponseDTO;
import edu.icet.model.entity.Seat;
import edu.icet.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public String bookingSeat(SeatHoldRequestDTO requestDTO) {

        seatRepository.expireUnpaidHolds();

        Seat seat = seatRepository.searchSeat(requestDTO.getSeat_number());

        if (seat == null) {
            return "Seat not found";
        }

        if (!"AVAILABLE".equals(seat.getStatus())) {
            return "Seat is not available";
        }

        seat.setStatus("HELD");
        seat.setHeldByUserId(requestDTO.getUserId());
        seat.setHoldExpiry(LocalDateTime.now().plusMinutes(10));

        seatRepository.bookSeat(seat);
        return null;
    }

    public List<SeatHoldResponseDTO> getAvailableSeats(long eventId) {

        // 1️⃣ Release expired holds
        seatRepository.expireUnpaidHolds();

        // 2️⃣ Fetch available seats
        return seatRepository.getAvailableSeats(eventId);
    }

}
