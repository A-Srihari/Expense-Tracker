package com.srihari.expensetracker.service;

import com.srihari.expensetracker.dto.ExpenseRequest;
import com.srihari.expensetracker.model.Expense;
import com.srihari.expensetracker.model.User;
import com.srihari.expensetracker.repository.ExpenseRepository;
import com.srihari.expensetracker.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    // ADD EXPENSE
    public Expense addExpense(Long userId, ExpenseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    // GET ALL EXPENSES
    public List<Expense> getExpenses(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    // TOTAL AMOUNT
    public double getTotalAmount(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // DELETE EXPENSE
    public void deleteExpense(Long expenseId, Long userId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        expenseRepository.delete(expense);
    }

    // UPDATE EXPENSE
    public Expense updateExpense(Long expenseId, Long userId, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());

        return expenseRepository.save(expense);
    }
}
