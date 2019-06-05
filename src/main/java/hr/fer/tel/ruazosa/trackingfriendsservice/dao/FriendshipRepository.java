package hr.fer.tel.ruazosa.trackingfriendsservice.dao;

import hr.fer.tel.ruazosa.trackingfriendsservice.models.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, String> {

    /**
     * Save friendship for each user in connection,
     * see more info in model.Friendship.
     *
     * @param f Friendship object
     */
    default void saveAcceptedFriendship(Friendship f) {
        // save record in both ways
        save(new Friendship(f.getUserId2(), f.getUserId1(), f.getFriendshipStatus()));
        save(f);
    }

    /**
     * Find all user's friends.
     *
     * @param userId1 logged in user
     * @return list of user's friendships with flag accepted
     */
    @Query("{ 'userId1' : ?0 , 'friendshipStatus' : 'ACCEPTED' }")
    List<Friendship> findAllFriends(String userId1);

    /**
     * Find pending friend requests to currently logged in user.
     * Currently logged in user can accept or deny pending request.
     *
     * @param userId2 this user (logged in)
     * @return list of user's friendships with flag pending
     */
    @Query("{ 'userId2' : ?0 , 'friendshipStatus' : 'PENDING' }")
    List<Friendship> findPendingFriendRequests(String userId2);

    /**
     * Find any connections between two users,
     * like pending friend request and accepted friend request.
     *
     * @param userId1 user id
     * @param userId2 user id
     * @return list of Friendship objects
     */
    @Query("{ 'userId1' : { $in : [ ?0 , ?1 ] } , 'userId2' : { $in : [ ?0 , ?1 ] } }")
    List<Friendship> findConnections(String userId1, String userId2);

}
