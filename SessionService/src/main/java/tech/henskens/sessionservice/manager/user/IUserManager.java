package tech.henskens.sessionservice.manager.user;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.dto.user.GetUserDto;
import tech.henskens.sessionservice.model.User;

public interface IUserManager {
    ResponseEntity<CreateUserDto> handleUser(FirebaseToken decodedToken);

    Page<GetUserDto> getAllUsers(Pageable pageable);

    GetUserDto updateUser(Long id, GetUserDto userDto);

    User authenticatedUser(String bearerToken);

}