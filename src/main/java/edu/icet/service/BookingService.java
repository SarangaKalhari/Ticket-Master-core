package edu.icet.service;

import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.model.entity.Booking;
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
        } else {
            return "The seat is currently available. Please proceed to book the seat.";

        }

    }
}
