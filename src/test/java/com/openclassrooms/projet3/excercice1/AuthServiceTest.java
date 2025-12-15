package com.openclassrooms.projet3.excercice1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.projet3.excercice1.dto.AuthResponse;
import com.openclassrooms.projet3.excercice1.dto.LoginRequest;
import com.openclassrooms.projet3.excercice1.dto.RegisterRequest;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceAlreadyExistsException;
import com.openclassrooms.projet3.excercice1.mapper.UserMapper;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import com.openclassrooms.projet3.excercice1.service.AuthService;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.JwtService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - AuthService")
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private CustomUserDetailsService userDetailsService;

	@InjectMocks
	private AuthService authService;

	private RegisterRequest registerRequest;
	private LoginRequest loginRequest;
	private User user;

	@BeforeEach
	void setUp() {
		registerRequest = new RegisterRequest("John Doe", "john@test.com", "password123");
		loginRequest = new LoginRequest("john@test.com", "password123");

		user = new User();
		user.setId(1L);
		user.setName("John Doe");
		user.setEmail("john@test.com");
		user.setPassword("$2a$10$encodedPassword");
	}

	@Test
	@DisplayName("Register - Email existant doit lever ResourceAlreadyExistsException (409)")
	void register_WithExistingEmail_ShouldThrowConflict409() {
		// Arrange
		when(userRepository.existsByEmail("john@test.com")).thenReturn(true);

		// Act & Assert - 409 CONFLICT
		ResourceAlreadyExistsException exception = assertThrows(
				ResourceAlreadyExistsException.class,
				() -> authService.register(registerRequest));

		assertThat(exception.getMessage()).contains("email");
		verify(userRepository).existsByEmail("john@test.com");
	}

	@Test
	@DisplayName("Login - Credentials invalides doit lever BadCredentialsException (401)")
	void login_WithInvalidCredentials_ShouldThrowUnauthorized401() {
		// Arrange
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new BadCredentialsException("Invalid credentials"));

		// Act & Assert - 401 UNAUTHORIZED
		assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

}
