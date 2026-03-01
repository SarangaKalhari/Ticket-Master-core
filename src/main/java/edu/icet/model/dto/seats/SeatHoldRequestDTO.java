package edu.icet.model.dto.seats;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatHoldRequestDTO {

    private Long userId;
    private String seat_number;
}
