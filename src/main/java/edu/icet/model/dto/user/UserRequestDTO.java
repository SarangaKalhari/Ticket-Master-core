package edu.icet.model.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDTO {

    private String name;
    private String email;
    private String tier;

}
