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
        setDisplayName(displayName);
        setEmail(email);
        setPassword(password);
    }


    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ApiRequestException {
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new ApiRequestException("Invalid email address", HttpStatus.BAD_REQUEST);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ApiRequestException {
        if (validatePassword(password)) {
            this.password = password;
        } else {
            throw new ApiRequestException("Password must contain at least 8 characters", HttpStatus.BAD_REQUEST);
        }
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
     * Validate email address.
     *
     * @param email email
     * @return true if email is valid
     */
    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    /**
     * Validate password.
     *
     * @param password password
     * @return true if password is valid
     */
    private boolean validatePassword(String password) {
        return password.length() >= 8;
    }

}
