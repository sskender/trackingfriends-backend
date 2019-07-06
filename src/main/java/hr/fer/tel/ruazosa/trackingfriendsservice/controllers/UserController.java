package hr.fer.tel.ruazosa.trackingfriendsservice.controllers;

import hr.fer.tel.ruazosa.trackingfriendsservice.config.ApiConstants;
import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;
import hr.fer.tel.ruazosa.trackingfriendsservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(ApiConstants.BASE_API_URL + ApiConstants.BASE_USER_URL)
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    /* register */

    @PostMapping("/register")
    public ResponseEntity<UserPublicProfile> register(
            @Valid @RequestBody User user
    ) {
        UserPublicProfile newUserPublicProfile = userService.registerUser(user);

        return new ResponseEntity<>(newUserPublicProfile, HttpStatus.CREATED);
    }

    /* register */


    /* login */

    @PostMapping("/login")
    public ResponseEntity<UserPublicProfile> login(
            @Valid @RequestBody User user
    ) {
        UserPublicProfile newUserPublicProfile = userService.loginUser(user);

        return new ResponseEntity<>(newUserPublicProfile, HttpStatus.OK);
    }

    /* login */


    /* user public profile */

    @GetMapping("/{userId}")
    public ResponseEntity<UserPublicProfile> getUserPublicProfile(
            @Valid @PathVariable String userId
    ) {
        UserPublicProfile userPublicProfile = userService.getUserPublicProfileWithId(userId);

        return new ResponseEntity<>(userPublicProfile, HttpStatus.OK);
    }

    /* user public profile */


    /* update */

    @PostMapping("/{userId}/update")
    public ResponseEntity<Object> updateFields(
            @Valid @PathVariable String userId,
            @Valid @RequestBody User user
    ) {
        userService.updateUser(userId, user);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /* update */


    /* friendship */

    @GetMapping("/{userId}/friends")
    public ResponseEntity<Set<UserPublicProfile>> getFriends(
            @Valid @PathVariable String userId
    ) {

        // TODO paging

        Set<UserPublicProfile> friends = userService.getUsersFriends(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends/requests")
    public ResponseEntity<Set<UserPublicProfile>> getFriendRequests(
            @Valid @PathVariable String userId
    ) {

        // TODO paging

        Set<UserPublicProfile> friendRequests = userService.getUsersFriendRequests(userId);

        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Object> deleteFriendship(
            @Valid @PathVariable String userId,
            @Valid @PathVariable String friendId
    ) {
        userService.deleteFriendship(userId, friendId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{userId}/friends")
    public ResponseEntity<Object> sendFriendRequest(
            @Valid @PathVariable String userId,
            @Valid @RequestParam(value = "friendId", required = true) String friendId,
            @Valid @RequestParam(value = "action", required = true) String action
    ) {
        switch (action) {
            case ApiConstants.FRIEND_REQUEST_PARAM:
                userService.sendFriendRequest(userId, friendId);
                break;
            case ApiConstants.FRIEND_ACCEPT_PARAM:
                userService.acceptFriendRequest(userId, friendId);
                break;
            case ApiConstants.FRIEND_DENY_PARAM:
                userService.denyFriendRequest(userId, friendId);
                break;
            default:
                throw new ApiRequestException("Invalid action", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /* friendship */


    /* searching users */

    @GetMapping("/{userId}/search")
    public ResponseEntity<List<UserPublicProfile>> seachUsers(
            @Valid @PathVariable String userId,
            @Valid @RequestParam(value = "username", required = true) String username
    ) {

        // TODO paging

        List<UserPublicProfile> userPublicProfileList = userService.searchUsersByUsername(username);

        return new ResponseEntity<>(userPublicProfileList, HttpStatus.OK);
    }

    /* searching users */


}
