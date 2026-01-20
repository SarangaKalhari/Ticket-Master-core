package edu.icet.service;

import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.model.entity.Booking;
import edu.icet.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;



    public void getBooking(BookingRequestDTO requestDTO) {

//        ------search seat status-----
        String seatStatus = bookingRepository.searchOnHold(requestDTO);

        if (seatStatus == null) {
            throw new RuntimeException("Seat not held by this user");
        }

        if (!seatStatus.equals("HELD")) {
            throw new RuntimeException("Seat is not currently on hold");
        } else {
            throw new RuntimeException("Seat is AVAILABLE now!!");
        }

    }
}
