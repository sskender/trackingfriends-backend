package hr.fer.tel.ruazosa.trackingfriendsservice.dao;

import hr.fer.tel.ruazosa.trackingfriendsservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Check if email already exists in database for registration purposes.
     *
     * @param email user email
     * @return User object
     */
    Optional<User> findByEmail(String email);

    /**
     * Validate user login with email and password.
     *
     * @param email    user email
     * @param password user password
     * @return User object
     */
    @Query("{ 'email' : ?0 , 'password' : ?1 }")
    Optional<User> findByEmailAndPassword(String email, String password);

    /**
     * Search users by username.
     *
     * @param searchUsername username
     * @return list of User objects
     */
    @Query
    List<User> findByUsernameLike(String searchUsername);

}
