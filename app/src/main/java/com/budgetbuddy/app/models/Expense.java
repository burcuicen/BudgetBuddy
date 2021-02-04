package com.budgetbuddy.app.models;
//Author: Çağrıhan GÜNAY

public class Expense {

    public String name;
    public Integer estimate;
    public Integer expense;

    public Expense(String name, Integer estimate, Integer expense) {
        this.name = name;
        this.estimate = estimate;
        this.expense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }
}
