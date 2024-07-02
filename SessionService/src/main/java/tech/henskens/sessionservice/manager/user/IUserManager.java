package tech.henskens.sessionservice.manager.user;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.dto.user.GetUserDto;

public interface IUserManager {
    CreateUserDto handleUser(FirebaseToken decodedToken);

    Page<GetUserDto> getAllUsers(Pageable pageable);

    GetUserDto updateUser(Long id, GetUserDto userDto);
}