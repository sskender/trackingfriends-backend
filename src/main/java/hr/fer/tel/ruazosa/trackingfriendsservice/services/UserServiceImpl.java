package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.dao.FriendshipRepository;
import hr.fer.tel.ruazosa.trackingfriendsservice.dao.UserRepository;
import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.Friendship;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.FriendshipStatus;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public User getUserWithId(String userId) throws ApiRequestException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ApiRequestException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserPublicProfile getUserPublicProfileWithId(String userId) throws ApiRequestException {
        return getUserWithId(userId).craftUserPublicProfile();
    }

    @Override
    public UserPublicProfile registerUser(User user) throws ApiRequestException {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new ApiRequestException("Sorry, that email is already taken", HttpStatus.CONFLICT);
        } else {
            // remove id, database will create an id
            user.setUserId(null);

            // validate user before saving
            user.validateUserFields();

            return userRepository.save(user).craftUserPublicProfile();
        }
    }

    @Override
    public UserPublicProfile loginUser(String email, String password) throws ApiRequestException {

        // these fields are already validated

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

        if (optionalUser.isPresent()) {

            return optionalUser.get().craftUserPublicProfile();
        } else {
            throw new ApiRequestException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void updateUser(User user) throws ApiRequestException {
        User userToBeUpdated = getUserWithId(user.getUserId());

        if (user.getUsername() != null) {
            User.validateDisplayName(user.getUsername());
            userToBeUpdated.setUsername(user.getUsername());
        }

        if (user.getEmail() != null) {
            User.validateEmail(user.getEmail());
            userToBeUpdated.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            User.validatePassword(user.getPassword());
            userToBeUpdated.setPassword(user.getPassword());
        }

        userRepository.save(userToBeUpdated);
    }

    @Override
    public Set<UserPublicProfile> getUsersFriends(String userId) throws ApiRequestException {
        // get friends ids from database
        List<Friendship> friendshipList = friendshipRepository.findAllFriends(userId);

        // craft user public profile from friend ids
        Set<UserPublicProfile> friendsUserPublicProfileSet = new HashSet<>();

        for (Friendship f : friendshipList) {
            UserPublicProfile friendUserProfile = getUserWithId(f.getUserId2()).craftUserPublicProfile();
            friendsUserPublicProfileSet.add(friendUserProfile);
        }

        // return user public profiles of friends
        return friendsUserPublicProfileSet;
    }

    @Override
    public Set<UserPublicProfile> getUsersFriendRequests(String userId) throws ApiRequestException {
        // get friend requests ids from database
        List<Friendship> friendshipList = friendshipRepository.findPendingFriendRequests(userId);

        // craft user public profile from friend requests ids
        Set<UserPublicProfile> friendsUserPublicProfileSet = new HashSet<>();

        for (Friendship f : friendshipList) {
            UserPublicProfile friendRequestUserProfile = getUserWithId(f.getUserId1()).craftUserPublicProfile();
            friendsUserPublicProfileSet.add(friendRequestUserProfile);
        }

        // return user public profiles of friend requests
        return friendsUserPublicProfileSet;
    }

    @Override
    public void sendFriendRequest(String userId, String friendRequestId) throws ApiRequestException {

        // TODO check user exist
        // TODO check not friends and not send request already

        Friendship friendshipRequest = new Friendship(userId, friendRequestId, FriendshipStatus.PENDING);

        friendshipRepository.save(friendshipRequest);
    }

    @Override
    public void acceptFriendRequest(String userId, String friendRequestId) throws ApiRequestException {

        // TODO check pending request exist

        // delete pending request
        friendshipRepository.deletePendingFriendRequests(userId, friendRequestId);

        // save accepted friendship
        Friendship newFriendship = new Friendship(userId, friendRequestId, FriendshipStatus.ACCEPTED);
        friendshipRepository.saveAcceptedFriendship(newFriendship);
    }

    @Override
    public void denyFriendRequest(String userId, String friendRequestId) throws ApiRequestException {

        // TODO check pending request exist

        // delete pending request
        friendshipRepository.deletePendingFriendRequests(userId, friendRequestId);
    }

}
