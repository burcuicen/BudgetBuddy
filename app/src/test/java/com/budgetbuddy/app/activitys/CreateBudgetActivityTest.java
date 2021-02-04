package com.budgetbuddy.app.activitys;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CreateBudgetActivityTest {
    @Test
    public void checkExpenseTotal_ShouldReturnTrue_WhenIncomeIsGreaterThanExpense() {
        CreateBudgetActivity activity = new CreateBudgetActivity();
        //checkExpenseTotal should return true when income is greater than expense
        assertTrue(activity.checkExpenseTotal(100, 150));
    }
    @Test
    public void checkExpenseTotal_ShouldReturnFalse_WhenIncomeIsSmallerThanExpense() {
        CreateBudgetActivity activity = new CreateBudgetActivity();
        //checkExpenseTotal should return false when income is smaller than expense
        assertFalse(activity.checkExpenseTotal(100, 90));
    }
}