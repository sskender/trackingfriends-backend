package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.dao.LocationRepository;
import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;

    private final IUserService userService;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, IUserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    @Override
    public Location updateUserLocation(Location location) throws ApiRequestException {

        // check if user is registered
        // update location

        userService.checkIfUserIdExists(location.getUserId());

        return locationRepository.save(location);
    }

    @Override
    public Location getUserLocation(String userId, String friendId) throws ApiRequestException {

        // check if users are registered
        // check if friends
        // check if location exists

        userService.checkIfUserIdExists(userId);
        userService.checkIfUserIdExists(friendId);

        // TODO check if friends

        Location location = locationRepository.findByUserId(friendId);

        if (location != null) {
            return location;
        } else {
            throw new ApiRequestException("User with id " + friendId + " has not shared location yet", HttpStatus.NOT_FOUND);
        }
    }

}
