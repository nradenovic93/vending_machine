package nl.hand.made.vending.machine.service;

import nl.hand.made.vending.machine.model.request.UserCreate;
import nl.hand.made.vending.machine.model.request.UserPatch;
import nl.hand.made.vending.machine.model.response.UserResponse;
import nl.hand.made.vending.machine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getUser(String userName) {
    }

    public void createUser(UserCreate user) {
    }

    public void editUser(String userName, UserPatch user) {
    }

    public void deleteUser(String userName) {
    }
}
