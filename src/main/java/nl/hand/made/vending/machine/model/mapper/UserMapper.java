package nl.hand.made.vending.machine.model.mapper;

import nl.hand.made.vending.machine.model.Role;
import nl.hand.made.vending.machine.model.request.UserData;
import nl.hand.made.vending.machine.model.response.UserResponse;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse mapToResponseModel(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setDeposit(userEntity.getDeposit());
        userResponse.setRoles(userEntity.getRoles().stream().map(Role::fromString).collect(Collectors.toList()));
        return userResponse;
    }

    public static UserEntity mapToEntityModel(UserData userData, PasswordEncoder passwordEncoder) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userData.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
        userEntity.setDeposit(0);
        userEntity.setRoles(userData.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
        return userEntity;
    }

    public static UserEntity patch(UserEntity userEntity, UserData userData, PasswordEncoder passwordEncoder) {
        if (StringUtils.isNotBlank(userData.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
        }

        if (userData.getRoles() != null && !userData.getRoles().isEmpty() && userData.getRoles().stream().anyMatch(Objects::nonNull)) {
            userEntity.setRoles(userData.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
        }

        return userEntity;
    }
}
