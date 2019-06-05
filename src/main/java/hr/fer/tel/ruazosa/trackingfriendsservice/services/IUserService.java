package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;

public interface IUserService extends IFriendshipService {

    /**
     * Return user object from database if it exists in database.
     * (check if user is registered in system)
     *
     * @param userId user id
     * @return User object
     * @throws ApiRequestException
     */
    User getUserWithId(String userId) throws ApiRequestException;

    /**
     * Return user public profile from given user id.
     *
     * @param userId user id
     * @return user public profile
     * @throws ApiRequestException
     */
    UserPublicProfile getUserPublicProfileWithId(String userId) throws ApiRequestException;

    /**
     * Register new user on service.
     *
     * @param user User object
     * @return new user id
     * @throws ApiRequestException
     */
    UserPublicProfile registerUser(User user) throws ApiRequestException;

    /**
     * Login existing user into service.
     *
     * @param user User object
     * @return user id
     * @throws ApiRequestException
     */
    default UserPublicProfile loginUser(User user) throws ApiRequestException {

        User.validateEmail(user.getEmail());
        User.validatePassword(user.getPassword());

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
    UserPublicProfile loginUser(String email, String password) throws ApiRequestException;

    /**
     * Update user as object.
     *
     * @param user user object
     * @throws ApiRequestException
     */
    void updateUser(User user) throws ApiRequestException;

}
