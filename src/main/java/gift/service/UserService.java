package gift.service;

import gift.common.auth.JwtTokenProvider;
import gift.common.exception.ExistUserException;
import gift.common.exception.UserNotFoundException;
import gift.controller.user.dto.UserRequest;
import gift.model.User;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Long register(UserRequest.Create userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ExistUserException();
        }
        User user = userRepository.save(userRequest.toEntity());
        return user.getId();
    }

    public String login(UserRequest.Update userRequest) {
        User user = userRepository.findByEmail(userRequest.email()).orElseThrow(UserNotFoundException::new);
        return jwtTokenProvider.createToken(user.getEmail());
    }
}
