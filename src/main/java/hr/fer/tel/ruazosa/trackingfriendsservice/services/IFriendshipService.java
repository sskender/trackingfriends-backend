package hr.fer.tel.ruazosa.trackingfriendsservice.services;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import hr.fer.tel.ruazosa.trackingfriendsservice.models.UserPublicProfile;

import java.util.List;
import java.util.Set;

public interface IFriendshipService {

    List<UserPublicProfile> getUsersFriends(String userId, int start) throws ApiRequestException;

    List<UserPublicProfile> getUsersFriendRequests(String userId, int start) throws ApiRequestException;

    void sendFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

    void acceptFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

    void denyFriendRequest(String userId, String friendRequestId) throws ApiRequestException;

}
