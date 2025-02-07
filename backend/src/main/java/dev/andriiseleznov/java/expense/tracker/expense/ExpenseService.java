package dev.andriiseleznov.java.expense.tracker.expense;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.andriiseleznov.java.expense.tracker.user.User;
import dev.andriiseleznov.java.expense.tracker.user.UserRepository;
import dev.andriiseleznov.java.expense.tracker.util.JwtUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    private User getAuthenticatedUser(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public Expense addExpense(String token, Expense expense) {
        User user = getAuthenticatedUser(token);
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(String token) {
        return expenseRepository.findByUserId(getAuthenticatedUser(token).getId());
    }

    public List<Expense> getExpensesByCategory(String token, String category) {
        return expenseRepository.findByUserIdAndCategory(getAuthenticatedUser(token).getId(), category);
    }

    public Double getTotalSpent(String token, LocalDate from, LocalDate till) {
		String username = jwtUtil.extractUsername(token);
		Optional<User> user = userRepository.findByUsername(username);
	
		if (user.isEmpty()) {
			System.out.println("User not found for token: " + token);
			return 0.0;
		}
	
		Double total = expenseRepository.getTotalSpending(user.get().getId(), from, till);
		return total != null ? total : 0.0;  // Prevent returning null
        // return expenseRepository.getTotalSpending(getAuthenticatedUser(token).getId(), from, till);
    }

    public List<Object[]> getDailySummary(String token, LocalDate from, LocalDate till) {
        return expenseRepository.getDailySummary(getAuthenticatedUser(token).getId(), from, till);
    }

    public Expense updateExpense(String token, Long id, Expense expenseData) {
        User user = getAuthenticatedUser(token);
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (!existingExpense.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        existingExpense.setAmount(expenseData.getAmount());
        existingExpense.setCategory(expenseData.getCategory());
        existingExpense.setDescription(expenseData.getDescription());
        existingExpense.setDate(expenseData.getDate());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(String token, Long id) {
        User user = getAuthenticatedUser(token);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        expenseRepository.delete(expense);
    }
}