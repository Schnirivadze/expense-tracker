package dev.andriiseleznov.java.expense.tracker.expense;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.andriiseleznov.java.expense.tracker.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

	@Id
	@SequenceGenerator(name = "expense_sequence", sequenceName = "expense_sequence", allocationSize = 1)
	@GeneratedValue(generator = "expense_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "description")
	private String description;

	@Column(name = "category")
	private String category;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore 
	private User user;

	public Expense(Double amount, String description, String category, User user) {
		this.amount = amount;
		this.description = description;
		this.category = category;
		this.user = user;
	}

	public Expense() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "{\n" + //
				"    \"amount\": "+this.amount+",\n" + //
				"    \"description\": \""+this.description+"\",\n" + //
				"    \"category\": \""+this.category+"\",\n" + //
				"    \"date\": \""+this.date.toString()+"\"\n" + //
				"}";
	}

	public String toStringUser() {
		return "{\n" + //
				"    \"amount\": "+this.amount+",\n" + //
				"    \"description\": \""+this.description+"\",\n" + //
				"    \"category\": \""+this.category+"\",\n" + //
				"    \"date\": \""+this.date.toString()+"\"\n" + //
				"    \"user_id\": \""+this.user.getId()+"\"\n" + //
				"}";
	}

}
