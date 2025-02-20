package ch.devprojects.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Configuration → Marks this as a Spring configuration class.
 * @Bean SecurityFilterChain → Defines security rules.
 * csrf.disable() → Disables CSRF (needed for non-browser clients).
 * authorizeHttpRequests().requestMatchers("/**").permitAll() → Allows all API endpoints without login.
 * formLogin().disable() → Disables Spring Boot’s login form.
 * httpBasic().disable() → Disables HTTP basic authentication.
 * 
 */

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF for development
				.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll() // Allow all requests without
																						// authentication
				).formLogin(login -> login.disable()) // Disable default login form
				.httpBasic(basic -> basic.disable()); // Disable basic authentication

		return http.build();
	}
}