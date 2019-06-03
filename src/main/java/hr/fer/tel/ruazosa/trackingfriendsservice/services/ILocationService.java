package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.Location;

public interface ILocationService {

    /**
     * Save or update user's current location.
     *
     * @param location Location object
     * @return saved Location object
     * @throws ApiRequestException
     */
    Location updateUserLocation(Location location) throws ApiRequestException;

    /**
     * Grab user's current location.
     *
     * @param userId   user id who is sending request
     * @param friendId friend id whose location is wanted
     * @return Location object
     * @throws ApiRequestException
     */
    Location getUserLocation(String userId, String friendId) throws ApiRequestException;

}
