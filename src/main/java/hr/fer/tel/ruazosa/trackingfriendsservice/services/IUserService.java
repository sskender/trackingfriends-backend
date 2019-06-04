package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;

public interface IUserService {

    /**
     * Check if user with given id exists in database.
     * (check if user is registered in system)
     *
     * @param userId user id
     * @return User object
     * @throws ApiRequestException
     */
    User checkIfUserIdExists(String userId) throws ApiRequestException;

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

        user.validateEmail(user.getEmail());
        user.validatePassword(user.getPassword());

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

    /**
     * Update user as object.
     *
     * @param user user object
     * @throws ApiRequestException
     */
    void updateUser(User user) throws ApiRequestException;

    /**
     * Update user's display name.
     *
     * @param userId      user id
     * @param displayName new display name
     * @throws ApiRequestException
     */
    void updateDisplayName(String userId, String displayName) throws ApiRequestException;

    /**
     * Update user's email.
     *
     * @param userId user id
     * @param email  new email
     * @throws ApiRequestException
     */
    void updateEmail(String userId, String email) throws ApiRequestException;

    /**
     * Update user's password.
     *
     * @param userId   user id
     * @param password new password
     * @throws ApiRequestException
     */
    void updatePassword(String userId, String password) throws ApiRequestException;

}
