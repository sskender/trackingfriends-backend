package hr.fer.tel.ruazosa.trackingfriendsservice.models;

import com.mongodb.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class UserPublicProfile implements Serializable {

    private String userId;
    private String displayName;

    public UserPublicProfile(@NonNull String userId, @NonNull String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
                ", displayName='" + displayName + '\'' +
                '}';
    }

}
