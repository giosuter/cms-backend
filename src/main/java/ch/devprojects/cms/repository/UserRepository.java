package ch.devprojects.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.devprojects.cms.model.User;

/**
 * Repository interface for User entity.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a User by their username.
     * @param username the username of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Finds a User by their email.
     * @param email the email of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
    
    Optional<User> deleteByEmail(String email);
}