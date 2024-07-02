package tech.henskens.sessionservice.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.henskens.sessionservice.model.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private long id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime updated;
}

