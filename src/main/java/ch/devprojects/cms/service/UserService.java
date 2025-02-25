package ch.devprojects.cms.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.devprojects.cms.model.Role;
import ch.devprojects.cms.model.User;
import ch.devprojects.cms.repository.RoleRepository;
import ch.devprojects.cms.repository.UserRepository;
import jakarta.annotation.PostConstruct;

/**
 * Service class for user management, including registration and retrieval.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     * @param username the username for the new user.
     * @param email the email for the new user.
     * @param password the password for the new user.
     * @return the newly created User object.
     */
    public User registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        
        // Fetch ROLE_USER from the database
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found in database"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole); // Assign default role
        user.setRoles(roles);

        return userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     * @param username the username to search for.
     * @return the User object if found.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    /**
     * Finds a user by their email.
     * @param email the email to search for.
     * @return the User object if found.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
    
    /**
     * Initializes default roles if they do not exist.
     */
    @PostConstruct
    public void initRoles() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
    }
}