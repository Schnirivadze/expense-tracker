package dev.andriiseleznov.java.expense.tracker.user;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.andriiseleznov.java.expense.tracker.util.JwtUtil;

@RestController
@RequestMapping(path = "/auth")
public class UserController {
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtil jwtUtil;

	public UserController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		if (userService.getUserByUsername(user.getUsername()).isPresent()
				|| userService.getUserByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.status(400).body("Registration failed: Login or email is already taken.");
		}

		try {
			userService.createUser(user);
			return ResponseEntity.status(201).body("User registered successfully.");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));
		String token = jwtUtil.generateToken(authentication.getName());
		return ResponseEntity.ok(token);
	}

	@GetMapping("/me")
	public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) {
		String login = jwtUtil.extractUsername(token);
		System.out.println("Received token: " + token);
		System.out.println("Extracted username: " + jwtUtil.extractUsername(token));

		if (login == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Optional<User> user = userService.getUserByUsername(login);
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(user.get());
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token,
			@RequestBody User updatedUser) {
		String login = jwtUtil.extractUsername(token);
		if (login == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Optional<User> user = userService.getUserByUsername(login);
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		userService.updateUser(user.get().getId(), updatedUser);
		return ResponseEntity.ok("User updated successfully");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
		String login = jwtUtil.extractUsername(token);
		if (login == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Optional<User> user = userService.getUserByUsername(login);
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		userService.deleteUser(user.get().getId());
		return ResponseEntity.ok("User deleted successfully");
	}
}
