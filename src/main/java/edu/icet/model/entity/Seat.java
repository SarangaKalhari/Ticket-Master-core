package edu.icet.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Seat {

    private long id;
    private long eventId;
    private String seatNumber;
    private String status; // AVAILABLE, HELD, SOLD
    private Long heldByUserId;
    private LocalDateTime holdExpiry;

}
