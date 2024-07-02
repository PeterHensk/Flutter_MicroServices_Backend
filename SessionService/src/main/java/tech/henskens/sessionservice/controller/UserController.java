package tech.henskens.sessionservice.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.henskens.sessionservice.dto.user.CreateUserDto;
import tech.henskens.sessionservice.dto.user.GetUserDto;
import tech.henskens.sessionservice.manager.user.IUserManager;

@RestController
@RequestMapping({"/user"})
public class UserController {
    private final IUserManager userManager;

    public UserController(IUserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping({"/whoami"})
    public CreateUserDto whoAmI(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String idTokenString = authorizationHeader.substring(7);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        return this.userManager.handleUser(decodedToken);
    }

    @GetMapping
    public Page<GetUserDto> getAllUsers(Pageable pageable) {
        return this.userManager.getAllUsers(pageable);
    }

    @PutMapping({"/{id}"})
    public GetUserDto updateUser(@PathVariable Long id, @RequestBody GetUserDto userDto) {
        return this.userManager.updateUser(id, userDto);
    }
}

