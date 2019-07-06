package hr.fer.tel.ruazosa.trackingfriendsservice.models;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Friendship models are saved in Mongo DB.
 * <p>
 * There are two records for each friendship. E.g.:
 * {userId1, userId2, ACCEPTED}
 * {userId2, userId1, ACCEPTED}
 * <p>
 * There is one record for each friend request. E.g.:
 * {userId1, userId2, PENDING}
 */
@Document(collection = "Friendships")
public class Friendship implements Serializable {

    private String userId1;
    private String userId2;
    private FriendshipStatus friendshipStatus;

    public Friendship() {
        super();
    }

    public Friendship(@NotNull String userId1, @NotNull String userId2, @NotNull FriendshipStatus friendshipStatus) throws ApiRequestException {
        if (userId1.equals(userId2)) {
            throw new ApiRequestException("Invalid request, cannot be friends with yourself", HttpStatus.BAD_REQUEST);
        }
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.friendshipStatus = friendshipStatus;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return getUserId1().equals(that.getUserId1()) &&
                getUserId2().equals(that.getUserId2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId1(), getUserId2());
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userId1='" + userId1 + '\'' +
                ", userId2='" + userId2 + '\'' +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }

}
