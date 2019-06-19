package hr.fer.tel.ruazosa.trackingfriendsservice.controllers;

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
@RequestMapping(BaseApiUrl.BASE_API_URL + "/user")
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

    @PostMapping("/{userId}/friends")
    public ResponseEntity<Object> sendFriendRequest(
            @Valid @PathVariable String userId,
            @Valid @RequestParam(value = "friendId", required = true) String friendId,
            @Valid @RequestParam(value = "action", required = true) String action
    ) {
        switch (action) {
            case "request":
                userService.sendFriendRequest(userId, friendId);
                break;
            case "accept":
                userService.acceptFriendRequest(userId, friendId);
                break;
            case "deny":
                userService.denyFriendRequest(userId, friendId);
                break;
            default:
                throw new ApiRequestException("Invalid action", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /* friendship */


    /* searching users */

    @GetMapping("/search")
    public ResponseEntity<List<UserPublicProfile>> seachUsers(
            @Valid @RequestParam(value = "username", required = true) String username
    ) {

        // TODO paging

        List<UserPublicProfile> userPublicProfileList = userService.searchUsersForFriends(username);

        return new ResponseEntity<>(userPublicProfileList, HttpStatus.OK);
    }

    /* searching users */


}
