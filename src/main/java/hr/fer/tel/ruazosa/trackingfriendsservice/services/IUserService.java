package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;

import java.util.List;

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
        //User.validatePassword(user.getPassword());

        return loginUser(user.getEmail(), user.getPassword());
    }

    /**
     * Login existing user into service.
     *
     * @param email    user email
     * @param rawPassword user password
     * @return user id
     * @throws ApiRequestException
     */
    UserPublicProfile loginUser(String email, String rawPassword) throws ApiRequestException;

    /**
     * Update user as object.
     *
     * @param userId user id
     * @param user   user object
     * @throws ApiRequestException
     */
    default void updateUser(String userId, User user) throws ApiRequestException {
        user.setUserId(userId);
        updateUser(user);
    }

    /**
     * Update user as object.
     *
     * @param user user object
     * @throws ApiRequestException
     */
    void updateUser(User user) throws ApiRequestException;

    /**
     * Search database for users with username like usernameToSearch.
     *
     * @param usernameToSearch username to search for
     * @return list of user public profiles
     * @throws ApiRequestException
     */
    List<UserPublicProfile> searchUsersByUsername(String usernameToSearch) throws ApiRequestException;

}
