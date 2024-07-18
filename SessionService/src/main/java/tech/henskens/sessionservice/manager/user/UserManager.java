package tech.henskens.sessionservice.manager.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.dto.user.GetUserDto;
import tech.henskens.sessionservice.mapper.user.UserMapper;
import tech.henskens.sessionservice.mapper.user.UserToGetUserDtoMapper;
import tech.henskens.sessionservice.model.User;
import tech.henskens.sessionservice.repository.IUserRepository;

@Service
public class UserManager implements IUserManager {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    public UserManager(IUserRepository userRepository, UserMapper userMapper, UserToGetUserDtoMapper userToGetUserDtoMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
    }

    @Override
    public ResponseEntity<CreateUserDto> handleUser(FirebaseToken decodedToken) {
        CreateUserDto createUserDto = userMapper.firebaseTokenToCreateUserDto(decodedToken);
        User user = this.userRepository.findByEmailAddress(createUserDto.getEmailAddress());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (user == null) {
            user = this.userMapper.createUserDtoToUser(createUserDto);
            user.setCreated(currentTimestamp.toLocalDateTime());
        }

        user.setUpdated(currentTimestamp.toLocalDateTime());
        this.userRepository.save(user);
        CreateUserDto createdUserDto = this.userMapper.userToCreateUserDto(user);
        if (createdUserDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
        }
    }

    public Page<GetUserDto> getAllUsers(Pageable pageable) {
        Page<User> usersPage = this.userRepository.findAllByOrderByUpdatedDesc(pageable);
        UserToGetUserDtoMapper var10001 = this.userToGetUserDtoMapper;
        Objects.requireNonNull(var10001);
        return usersPage.map(var10001::userToGetUserDto);
    }

    public GetUserDto updateUser(Long id, GetUserDto userDto) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
        user.setEmailAddress(userDto.getEmailAddress());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setUpdated(userDto.getUpdated());
        User updatedUser = this.userRepository.save(user);
        return this.userToGetUserDtoMapper.userToGetUserDto(updatedUser);
    }

    @Override
    public User authenticatedUser(String bearerToken) {
        try {
            String idToken = bearerToken.substring(7);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            User user = userRepository.findByEmailAddress(email);
            if (user == null) {
                throw new NoSuchElementException("User with email " + email + " does not exist");
            }
            return user;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
