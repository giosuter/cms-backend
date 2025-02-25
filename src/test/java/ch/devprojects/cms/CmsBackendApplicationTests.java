package ch.devprojects.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.devprojects.cms.model.Role;
import ch.devprojects.cms.model.User;
import ch.devprojects.cms.repository.RoleRepository;
import ch.devprojects.cms.repository.UserRepository;
import ch.devprojects.cms.service.UserService;

@SpringBootTest
public class CmsBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Inject RoleRepository to fetch roles

    @Autowired
    private UserService userService;

    @AfterEach
    public void cleanup() {
        userRepository.findByEmail("test@example.com").ifPresent(user -> userRepository.deleteById(user.getId()));
    }

    @Test
    public void testUserRegistration() {
        // Generate unique username and email to avoid conflicts
        String uniqueUsername = "testuser" + System.currentTimeMillis();
        String uniqueEmail = "test" + System.currentTimeMillis() + "@example.com";

        // Fetch or create ROLE_USER from the database
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole); // Save the role if not found
                });

        // Create a new user with unique credentials
        User user = new User(uniqueUsername, uniqueEmail, "password123");

        Set<Role> roles = new HashSet<>();
        roles.add(userRole); // Assign default role
        user.setRoles(roles);

        // Save user and assert
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertEquals(uniqueUsername, savedUser.getUsername());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }
}