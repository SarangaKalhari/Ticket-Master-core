package edu.icet.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Seat {

    private long id;
    private long event_id;
    private String seat_number;
    private String status;
    private long user_id;

}
