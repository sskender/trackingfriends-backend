package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;

public interface IUserService {

    /**
     * Check if user with given id exists in database.
     * (check if user is registered in system)
     *
     * @param userId user id
     */
    void checkIfUserIdExists(String userId) throws ApiRequestException;

    /**
     * Register new user on service.
     *
     * @param user User object
     * @return new user id
     * @throws ApiRequestException
     */
    String registerUser(User user) throws ApiRequestException;

    /**
     * Login existing user into service.
     *
     * @param user User object
     * @return user id
     * @throws ApiRequestException
     */
    default String loginUser(User user) throws ApiRequestException {

        user.validateUserFields();

        return loginUser(user.getEmail(), user.getPassword());
    }

    /**
     * Login existing user into service.
     *
     * @param email    user email
     * @param password user password
     * @return user id
     * @throws ApiRequestException
     */
    String loginUser(String email, String password) throws ApiRequestException;

}
