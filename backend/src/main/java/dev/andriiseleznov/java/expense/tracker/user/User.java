package dev.andriiseleznov.java.expense.tracker.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false, unique = false)
	private String password;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "current_balance", nullable = false, unique = false)
	private Float currentBalance;

	public User(String username, String password, String email, Float currentBalance) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.currentBalance = currentBalance;
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.currentBalance = 0f;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Float currentBalance) {
		this.currentBalance = currentBalance;
	}

}
