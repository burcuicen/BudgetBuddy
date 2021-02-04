package com.budgetbuddy.app.activitys;
//Author: Burcu İÇEN, Çağrıhan GÜNAY

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.adapters.CategoryEntryAdapter;
import com.budgetbuddy.app.databases.Budgets;
import com.budgetbuddy.app.models.Expense;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class CreateBudgetActivity extends Activity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    public EditText incomeEt;
    private ArrayList<Expense> expenses;
    private Budgets database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_createbucket);
        super.onCreate(savedInstanceState);

        expenses = new ArrayList<>();

        incomeEt = findViewById(R.id.createbucket_income_et);
        incomeEt.setHint("0");

        recyclerView = findViewById(R.id.createbucket_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CategoryEntryAdapter adapter = new CategoryEntryAdapter(expenses);
        recyclerView.setAdapter(adapter);
        expenses.add(new Expense(getString(R.string.createbucket_investment), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_shopping), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_payments), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_debts), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_education), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_entertainment), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_bills), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_rent_mortgage), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_kitchen), 0, 0));
        expenses.add(new Expense(getString(R.string.createbucket_transportation), 0, 0));
        adapter.notifyDataSetChanged();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cratebucket_bnv_menu_done) {
                    if (!incomeEt.getText().toString().equals(""))
                        if (Integer.parseInt(incomeEt.getText().toString()) != 0) {
                            int expenseTotal = 0;
                            for (int i = 0; i < expenses.size(); i++) {
                                expenseTotal = expenseTotal + expenses.get(i).getEstimate();
                            }

                            if (checkExpenseTotal(expenseTotal, Integer.parseInt(incomeEt.getText().toString()))) {
                                createBudget(Integer.parseInt(incomeEt.getText().toString()), expenses);
                            } else
                                Toast.makeText(CreateBudgetActivity.this, getString(R.string.error_income), Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(CreateBudgetActivity.this, getString(R.string.error_income), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(CreateBudgetActivity.this, getString(R.string.error_income), Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.cratebucket_bnv_menu_addcategory) {
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateBudgetActivity.this).create();
                    LayoutInflater layoutInflater = CreateBudgetActivity.this.getLayoutInflater();
                    View dialogView = layoutInflater.inflate(R.layout.dialog_addcategory, null);

                    EditText editText = dialogView.findViewById(R.id.dialog_addcategory_et);
                    MaterialButton materialButton = dialogView.findViewById(R.id.dialog_addcategory_button);

                    materialButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!editText.getText().toString().equals("")) {
                                if (getString(R.string.createbucket_shopping).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_payments).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_education).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_kitchen).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_bills).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_debts).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_entertainment).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_investment).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_rent_mortgage).equals(editText.getText().toString())) {
                                } else if (getString(R.string.createbucket_transportation).equals(editText.getText().toString())) {
                                } else {
                                    boolean b = false;
                                    for (int i = 0; i < expenses.size(); i++) {
                                        if (!expenses.get(i).getName().equals(editText.getText().toString()))
                                            b = true;
                                        else
                                            b = false;

                                        if (i + 1 == expenses.size()) {
                                            if (b) {
                                                expenses.add(new Expense(editText.getText().toString(), 0, 0));
                                                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                                                alertDialog.dismiss();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });

                    alertDialog.setView(dialogView);
                    alertDialog.show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        incomeEt = findViewById(R.id.createbucket_income_et);
    }

    public boolean checkExpenseTotal(int expenseTotal, int income) {
        return expenseTotal <= income;
    }

    private void createBudget(int income, ArrayList<Expense> expenses) {
        getApplicationContext().deleteDatabase(Budgets.DATABASE_NAME);
        database = new Budgets(CreateBudgetActivity.this);

        String id = String.valueOf(UUID.randomUUID());

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("income", income);
        contentValues.put("date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        sqLiteDatabase.insertOrThrow("budgets", null, contentValues);

        for (Expense expense : expenses) {
            if (expense.getEstimate() != 0) {
                ContentValues cv = new ContentValues();
                cv.put("name", expense.getName());
                cv.put("estimate", expense.getEstimate() == null ? 0 : expense.getEstimate());
                cv.put("expense", expense.getExpense() == null ? 0 : expense.getExpense());
                sqLiteDatabase.insertOrThrow("expenses", null, cv);
            }
        }

        startActivity(new Intent(CreateBudgetActivity.this, MainActivity.class));
    }
}
