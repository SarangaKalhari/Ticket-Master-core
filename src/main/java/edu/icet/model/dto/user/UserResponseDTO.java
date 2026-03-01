package edu.icet.model.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String tier;
    private LocalDateTime createdAt;
}
