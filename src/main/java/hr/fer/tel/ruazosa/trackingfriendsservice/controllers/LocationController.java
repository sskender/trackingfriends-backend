package hr.fer.tel.ruazosa.trackingfriendsservice.controllers;

import hr.fer.tel.ruazosa.trackingfriendsservice.config.ApiConstants;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.Location;
import hr.fer.tel.ruazosa.trackingfriendsservice.services.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstants.BASE_API_URL + ApiConstants.BASE_USER_URL + "/{userId}/location")
public class LocationController {

    private final ILocationService locationService;

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{friendId}")
    public ResponseEntity<Location> getLocation(
            @Valid @PathVariable String userId,
            @Valid @PathVariable String friendId
    ) {
        Location location = locationService.getUserLocation(userId, friendId);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> updateLocation(
            @Valid @PathVariable String userId,
            @Valid @RequestBody Location location
    ) {
        location.setUserId(userId);
        Location updatedLocation = locationService.updateUserLocation(location);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
