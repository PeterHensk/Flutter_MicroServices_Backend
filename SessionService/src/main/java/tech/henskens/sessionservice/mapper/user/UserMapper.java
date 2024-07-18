package tech.henskens.sessionservice.mapper.user;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.model.Role;
import tech.henskens.sessionservice.model.User;

import java.util.Arrays;

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

    public CreateUserDto firebaseTokenToCreateUserDto(FirebaseToken decodedToken) {
        String email = decodedToken.getEmail();
        String fullName = (String) decodedToken.getClaims().get("name");
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length)) : "";
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setEmailAddress(email);
        createUserDto.setFirstName(firstName);
        createUserDto.setLastName(lastName);
        createUserDto.setRole("VIEWER");
        return createUserDto;
    }
}

