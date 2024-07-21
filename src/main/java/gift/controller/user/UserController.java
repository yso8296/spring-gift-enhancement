package gift.controller.user;

import gift.controller.user.dto.UserRequest;
import gift.controller.user.dto.UserResponse;
import gift.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRequest.Create request) {
        Long id = userService.register(request);
        return ResponseEntity.created(URI.create("/api/v1/user/" + id)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserRequest.Update request) {
        String token = userService.login(request);
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(token);
    }
}
