package tech.henskens.sessionservice.dto.user;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.henskens.sessionservice.model.Permission;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String role;
    private Set<Permission> permissions;
    private LocalDateTime created;
    private LocalDateTime updated;
}
