package dev.andriiseleznov.java.expense.tracker.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Transactional
	public void updateUser(Long id, User updatedUser) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("User not found"));

		// Only update login if it's changed
		if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
			existingUser.setUsername(updatedUser.getUsername());
		}

		// Only rehash and update password if it's provided and has changed
		if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
			if (!updatedUser.getPassword().equals(existingUser.getPassword())) {
				existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			}
		}
		
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setCurrentBalance(updatedUser.getCurrentBalance());

		userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public Optional<User> authenticate(String username, String rawPassword) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) {
			return user;
		}
		return Optional.empty();
	}
}
