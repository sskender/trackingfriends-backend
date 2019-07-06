package hr.fer.tel.ruazosa.trackingfriendsservice.models;

import hr.fer.tel.ruazosa.trackingfriendsservice.exceptions.ApiRequestException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User models are saved in Mongo DB
 */
@Document(collection = "Users")
public class User implements Serializable {


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Id
    private String userId;
    private String username;
    private String email;
    private String password;


    protected User() {

    }

    public User(@NotNull String userId, @NotNull String username, @NotNull String email, @NotNull String password) {
        this.userId = userId;
        this.username = username;
        this.email = email.toLowerCase();
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Validate display name.
     *
     * @param username display name
     * @throws ApiRequestException
     */
    public static void validateUsername(String username) throws ApiRequestException {
        if (username == null || username.isEmpty() || username.equals(" ")) {
            throw new ApiRequestException("Invalid display name", HttpStatus.BAD_REQUEST);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId().equals(user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Validate email address.
     *
     * @param email email
     * @throws ApiRequestException
     */
    public static void validateEmail(String email) throws ApiRequestException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find()) {
            throw new ApiRequestException("Invalid email address", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deprecated, this is not used anymore as passwords are now hashed.
     *
     *
     * Validate password.
     *
     * @param password password
     * @throws ApiRequestException
     */
    @Deprecated
    public static void validatePassword(String password) {
        if (!(password.length() >= 8)) {
            throw new ApiRequestException("Password must contain at least 8 characters", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validate user fields upon registration.
     *
     * @throws ApiRequestException
     */
    public void validateUserFields() throws ApiRequestException {
        validateUsername(getUsername());
        validateEmail(getEmail());
        // validatePassword(getPassword());
    }

    /**
     * Create user public profile from user object.
     * Public profile only contains user id and public display name.
     * Email and password must not be leaked.
     *
     * @return user public profile object
     */
    public UserPublicProfile craftUserPublicProfile() {
        return new UserPublicProfile(getUserId(), getUsername());
    }

}
