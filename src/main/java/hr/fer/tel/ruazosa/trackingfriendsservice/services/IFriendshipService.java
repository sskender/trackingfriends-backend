package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;

import java.util.Set;

public interface IFriendshipService {

    Set<UserPublicProfile> getUsersFriends(String userId) throws ApiRequestException;

    Set<UserPublicProfile> getUsersFriendRequests(String userId) throws ApiRequestException;

    void sendFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

    void acceptFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

    void denyFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

}
