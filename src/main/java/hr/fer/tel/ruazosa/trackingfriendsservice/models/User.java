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
    private String displayName;
    private String email;
    private String password;


    protected User() {

    }

    public User(@NotNull String userId, @NotNull String displayName, @NotNull String email, @NotNull String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Validate display name.
     *
     * @param displayName display name
     * @throws ApiRequestException
     */
    public void validateDisplayName(String displayName) throws ApiRequestException {
        if (displayName == null || displayName.isEmpty() || displayName.equals(" ")) {
            throw new ApiRequestException("Invalid display name", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validate email address.
     *
     * @param email email
     * @throws ApiRequestException
     */
    public void validateEmail(String email) throws ApiRequestException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find()) {
            throw new ApiRequestException("Invalid email address", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validate password.
     *
     * @param password password
     * @throws ApiRequestException
     */
    public void validatePassword(String password) {
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
        validateDisplayName(getDisplayName());
        validateEmail(getEmail());
        validatePassword(getPassword());
    }

}
