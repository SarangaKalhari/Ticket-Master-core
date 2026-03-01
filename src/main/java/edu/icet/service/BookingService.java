package edu.icet.service;

import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.model.dto.booking.BookingResponseDTO;
import edu.icet.model.dto.event.EventResponseDTO;
import edu.icet.model.entity.Seat;
import edu.icet.repository.BookingRepository;
import edu.icet.repository.EventRepository;
import edu.icet.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventRepository eventRepository;



    public String getBooking(BookingRequestDTO requestDTO) {
        seatRepository.expireUnpaidHolds();

//        ------search seat status-----
        String seatStatus = bookingRepository.searchOnHold(requestDTO);

        if (seatStatus == null) {
            return "Seat not held by this user";
        }

        if (!seatStatus.equals("HELD")) {
            return "Seat is not currently on hold";
//            bookingRepository.addBooking(requestDTO);
        } else {
            return "The seat is currently available. Please proceed to book the seat.";

        }

    }

    public String confirmBooking(BookingRequestDTO requestDTO, double amount) {

        // Step 1: Clear expired holds
        seatRepository.expireUnpaidHolds();

        // Step 2: Try to convert HELD → SOLD
        boolean success = bookingRepository.markHeldSeatAsSold(
                requestDTO.getSeatId(),
                requestDTO.getUserId()
        );

        if (!success) {
            return "Seat hold expired or not owned by user";
        }

        // Step 3: Insert booking record (after SOLD)
        bookingRepository.addBooking(requestDTO.getSeatId(), requestDTO.getUserId(), requestDTO.getAmount());

        return "Seat booked successfully (SOLD)";
    }


    public BookingResponseDTO bookSeat(Long userId, Long seatId) throws Exception {

        Seat seat = seatRepository.findById(seatId);

        if (seat == null) {
            throw new RuntimeException("Seat not found");
        }

        if (!"AVAILABLE".equals(seat.getStatus())) {
            throw new RuntimeException("Seat not available");
        }

        EventResponseDTO event = eventRepository.findById(seat.getEventId());

        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Event expired");
        }

        BigDecimal finalPrice = event.getBasePrice();

        if (event.isHighDemand()) {
            finalPrice = finalPrice.multiply(BigDecimal.valueOf(1.2));
        }

        // Update seat status
        seatRepository.updateStatus(seatId, "SOLD");

        // Save booking
        Long bookingId = bookingRepository.save(
                userId,
                event.getId(),
                seatId,
                finalPrice
        );

        return new BookingResponseDTO(
                bookingId,
                seat.getSeatNumber(),
                finalPrice,
                "CONFIRMED"
        );
    }

}
