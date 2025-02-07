package dev.andriiseleznov.java.expense.tracker.expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@PostMapping
	public ResponseEntity<Expense> addExpense(@RequestHeader("Authorization") String token,
			@RequestBody Expense expense) {
		try {
			Expense addedExpense = expenseService.addExpense(token, expense);
			return ResponseEntity.ok(addedExpense);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<Expense>> getAllExpenses(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(expenseService.getAllExpenses(token));
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestHeader("Authorization") String token,
			@PathVariable String category) {
		return ResponseEntity.ok(expenseService.getExpensesByCategory(token, category));
	}

	@GetMapping("/daily-summary")
	public ResponseEntity<Double> getDailySummary(@RequestHeader("Authorization") String token) {
		LocalDate today = LocalDate.now();
		System.out.println("/daily-summary: " + expenseService.getTotalSpent(token, today, today));
		return ResponseEntity.ok(expenseService.getTotalSpent(token, today, today));
	}

	@GetMapping("/weekly-summary")
	public ResponseEntity<Double> getWeeklySummary(@RequestHeader("Authorization") String token) {
		LocalDate now = LocalDate.now();
		LocalDate lastWeek = now.minusDays(7);
		System.out.println("/weekly-summary: " + expenseService.getTotalSpent(token, lastWeek, now));
		return ResponseEntity.ok(expenseService.getTotalSpent(token, lastWeek, now));
	}

	@GetMapping("/monthly-summary")
	public ResponseEntity<Double> getMonthlySummary(@RequestHeader("Authorization") String token) {
		LocalDate now = LocalDate.now();
		LocalDate lastMonth = now.minusMonths(1);
		System.out.println("/monthly-summary: " + expenseService.getTotalSpent(token, lastMonth, now));
		return ResponseEntity.ok(expenseService.getTotalSpent(token, lastMonth, now));
	}

	@GetMapping("/spent-summary")
	public ResponseEntity<Double> getSpentSummary(
			@RequestHeader("Authorization") String token,
			@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam("till") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate till) {
		return ResponseEntity.ok(expenseService.getTotalSpent(token, from, till));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Expense> updateExpense(
			@RequestHeader("Authorization") String token,
			@PathVariable Long id,
			@RequestBody Expense expense) {
		return ResponseEntity.ok(expenseService.updateExpense(token, id, expense));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteExpense(
			@RequestHeader("Authorization") String token,
			@PathVariable Long id) {
		expenseService.deleteExpense(token, id);
		return ResponseEntity.ok(Map.of("message", "Expense deleted successfully"));
	}
}