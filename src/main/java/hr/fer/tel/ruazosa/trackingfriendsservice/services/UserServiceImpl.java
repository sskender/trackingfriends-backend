package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.dao.UserRepository;
import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void checkIfUserIdExists(String userId) throws ApiRequestException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new ApiRequestException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String registerUser(User user) throws ApiRequestException {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new ApiRequestException("Sorry, that email is already taken", HttpStatus.CONFLICT);
        } else {

            // validate user before saving
            user.validateUserFields();

            return userRepository.save(user).getUserId();
        }
    }

    @Override
    public String loginUser(String email, String password) throws ApiRequestException {

        // these fields are already validated

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

        if (optionalUser.isPresent()) {

            return optionalUser.get().getUserId();
        } else {
            throw new ApiRequestException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

}