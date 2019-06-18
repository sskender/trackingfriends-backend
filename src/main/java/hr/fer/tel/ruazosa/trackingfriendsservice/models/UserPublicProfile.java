package hr.fer.tel.ruazosa.trackingfriendsservice.models;

import com.mongodb.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class UserPublicProfile implements Serializable {

    private String userId;
    private String username;

    public UserPublicProfile(@NonNull String userId, @NonNull String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPublicProfile)) return false;
        UserPublicProfile that = (UserPublicProfile) o;
        return getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "UserPublicProfile{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
