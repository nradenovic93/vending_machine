package nl.hand.made.vending.machine.service;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.exception.ValidationException;
import nl.hand.made.vending.machine.model.mapper.UserMapper;
import nl.hand.made.vending.machine.model.request.UserData;
import nl.hand.made.vending.machine.model.response.UserResponse;
import nl.hand.made.vending.machine.repository.UserRepository;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse getUser(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::mapToResponseModel)
                .orElse(null);
    }

    public void createUser(UserData user) {
        validate(user);

        userRepository.findByUsername(user.getUsername())
                .ifPresent(userEntity -> {
                    throw new ValidationException("user with provided username already exists");
                });
        userRepository.save(UserMapper.mapToEntityModel(user, passwordEncoder));
    }

    public void editUser(UserData user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .map(entity -> UserMapper.patch(entity, user, passwordEncoder))
                .orElseThrow(() -> new ValidationException("user with username not found"));

        userRepository.save(userEntity);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("user with username not found"));
        userRepository.deleteByUsername(username);
    }

    private void validate(UserData user) {
        List<String> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(user.getUsername())) {
            validationErrors.add("username cannot be empty");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            validationErrors.add("password cannot be empty");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty() || user.getRoles().stream().noneMatch(Objects::nonNull)) {
            validationErrors.add("roles cannot be empty");
        }

        if (!validationErrors.isEmpty()) {
            throw new ValidationException(String.join(", ", validationErrors));
        }
    }
}
