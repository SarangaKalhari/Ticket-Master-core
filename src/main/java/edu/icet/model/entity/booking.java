package edu.icet.model.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class booking {

    private long id;            // PK, auto-increment
    private long userId;        // FK → users.id
    private long seatId;        // FK → seats.id
    private BigDecimal amountPaid; // amount paid for the booking
    private String status;      // CONFIRMED / CANCELLED
    private LocalDateTime bookedAt; // timestamp of booking

}
