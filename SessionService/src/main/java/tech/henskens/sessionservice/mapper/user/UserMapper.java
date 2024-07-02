package tech.henskens.sessionservice.mapper.user;

import org.springframework.stereotype.Component;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.model.Role;
import tech.henskens.sessionservice.model.User;

@Component
public class UserMapper {
    public UserMapper() {
    }

    public CreateUserDto userToCreateUserDto(User user) {
        CreateUserDto dto = new CreateUserDto();
        dto.setEmailAddress(user.getEmailAddress());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole().name());
        dto.setCreated(user.getCreated());
        dto.setUpdated(user.getUpdated());
        dto.setPermissions(user.getRole().getPermissions());
        return dto;
    }

    public User createUserDtoToUser(CreateUserDto dto) {
        User user = new User();
        user.setEmailAddress(dto.getEmailAddress());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user.setCreated(dto.getCreated());
        user.setUpdated(dto.getUpdated());
        return user;
    }
}

