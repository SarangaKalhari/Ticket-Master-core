package edu.icet.model.dto.booking;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingRequestDTO {

    private Long userId;
    private Long seatId;
    private double amount;
}
