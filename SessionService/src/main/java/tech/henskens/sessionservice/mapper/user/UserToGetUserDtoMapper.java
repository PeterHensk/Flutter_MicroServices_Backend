package tech.henskens.sessionservice.mapper.user;

import org.springframework.stereotype.Component;
import tech.henskens.sessionservice.dto.user.GetUserDto;
import tech.henskens.sessionservice.model.User;

@Component
public class UserToGetUserDtoMapper {
    public UserToGetUserDtoMapper() {
    }

    public GetUserDto userToGetUserDto(User user) {
        GetUserDto dto = new GetUserDto();
        dto.setId(user.getId());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setUpdated(user.getUpdated());
        return dto;
    }
}

