package dev.andriiseleznov.java.expense.tracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByUserIdAndCategory(Long userId, String category);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :fromDate AND :toDate")
    Double getTotalSpending(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT e.date, SUM(e.amount) FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :fromDate AND :toDate GROUP BY e.date ORDER BY e.date ASC")
    List<Object[]> getDailySummary(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
