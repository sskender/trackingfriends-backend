package hr.fer.tel.ruazosa.trackingfriendsservice.controllers;

import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import hr.fer.tel.ruazosa.trackingfriendsservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseApiUrl.BASE_API_URL)
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /* register */

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody User user
    ) {
        String newUserId = userService.registerUser(user);

        return new ResponseEntity<>(newUserId, HttpStatus.CREATED);
    }

    /* register */


    /* login */

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody User user
    ) {
        String userId = userService.loginUser(user);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    /* login */


    /* update */

    @PostMapping("/{userId}/update")
    public ResponseEntity<Object> updateFields(
            @Valid @PathVariable String userId,
            @Valid @RequestBody User user
    ) {
        user.setUserId(userId);

        userService.updateUser(user);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /* update */


    // TODO search users
    // TODO friends bla bla


}
