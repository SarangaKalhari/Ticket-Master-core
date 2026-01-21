package edu.icet.model.dto.seats;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatHoldResponseDTO {

    private Long seatId;
    private Long eventId;
    private String seatNumber;
    private String status;
    private LocalDateTime holdExpiry;
}
