package ch.devprojects.cms.dto;

/**
 * DTO for authentication response containing token and message.
 */
public class AuthResponse {
	
    private String token;
    private String message; // Optional for error messages

    // Default constructor
    public AuthResponse() {}

    // Constructor for successful response with token
    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}