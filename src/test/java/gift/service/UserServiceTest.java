package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.controller.user.dto.UserRequest;
import gift.controller.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/truncate.sql")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입")
    void register() {
        UserRequest.Create userRequest = new UserRequest.Create("yso8296", "yso8296@gmail.com");

        Long id = userService.register(userRequest);

        assertAll(
            () -> assertThat(id).isNotNull()
        );
    }
}
