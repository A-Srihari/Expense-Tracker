package com.srihari.expensetracker.controller;

import com.srihari.expensetracker.dto.ExpenseRequest;
import com.srihari.expensetracker.model.Expense;
import com.srihari.expensetracker.service.ExpenseService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ADD
    @PostMapping
    public Expense addExpense(@Valid @RequestBody ExpenseRequest request,
                              HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        return expenseService.addExpense(userId, request);
    }


    // LIST + TOTAL
    @GetMapping
    public Map<String, Object> getExpenses(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        List<Expense> expenses = expenseService.getExpenses(userId);
        double total = expenseService.getTotalAmount(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("expenses", expenses);
        response.put("total", total);

        return response;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id,
                                HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        expenseService.deleteExpense(id, userId);

        return "Expense deleted";
    }

    // UPDATE
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @Valid @RequestBody ExpenseRequest request,
                                 HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        return expenseService.updateExpense(id, userId, request);
    }

}
