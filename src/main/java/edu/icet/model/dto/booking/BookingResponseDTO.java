package edu.icet.model.dto.booking;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingResponseDTO {

    private Long bookingId;
    private String seatNumber;
    private BigDecimal amountPaid;
    private String status;
}
