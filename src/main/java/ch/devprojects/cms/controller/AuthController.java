package ch.devprojects.cms.controller;

import ch.devprojects.cms.dto.AuthRequest;
import ch.devprojects.cms.dto.AuthResponse;
import ch.devprojects.cms.dto.RegisterRequest;
import ch.devprojects.cms.model.Role;
import ch.devprojects.cms.model.User;
import ch.devprojects.cms.repository.RoleRepository;
import ch.devprojects.cms.repository.UserRepository;
import ch.devprojects.cms.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, 
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        // Check for existing username
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            logger.error("Username is already taken: {}", registerRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken!");
        }

        // Check for existing email
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            logger.error("Email is already registered: {}", registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already registered!");
        }

        // Create a new user
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), 
                             registerRequest.getPassword());
        user.setPassword(registerRequest.getPassword()); // Password should be encoded, consider encoding it here

        // Fetch ROLE_USER from the database or create if not present
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole); // Save the role if not found
                });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole); // Assign default role
        user.setRoles(roles);

        // Save user
        userRepository.save(user);
        logger.info("User registered successfully: {}", registerRequest.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", authRequest.getUsername());
            // Use AuthResponse to structure the error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, "Invalid username or password"));
        }

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token, "Authencication successful for user: {}"));
    }
}
