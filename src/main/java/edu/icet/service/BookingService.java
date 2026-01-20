package edu.icet.service;

import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.repository.BookingRepository;
import edu.icet.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;



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

}
