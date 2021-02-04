package com.budgetbuddy.app.activitys;
//Author: Burcu İÇEN
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.databases.Budgets;
import com.budgetbuddy.app.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class AddEstimate extends Activity {

    private Spinner spinnerCategories;
    private ArrayAdapter<String> categoriesArrayAdapter;
    private Budgets database;
    private ArrayList<Expense> expenses;
    private List<String> categories;
    private EditText amountET;
    private int amountLimit, income, totalEstimate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_addestimate);
        super.onCreate(savedInstanceState);

        spinnerCategories = findViewById(R.id.addestimate_category);
        amountET = findViewById(R.id.addestimate_amount);
        database = new Budgets(this);

        categories = new ArrayList<>();
        expenses = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String[] query = new String[]{"name", "estimate", "expense"};
        Cursor cursor = sqLiteDatabase.query("expenses", query, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                expenses.add(new Expense(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("estimate")), cursor.getInt(cursor.getColumnIndex("expense"))));
                categories.add(cursor.getString(cursor.getColumnIndex("name")));
                totalEstimate = totalEstimate + cursor.getInt(cursor.getColumnIndex("estimate"));

                cursor.moveToNext();
            }
            cursor.close();

            categoriesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
            categoriesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategories.setAdapter(categoriesArrayAdapter);
        }

        String[] q = new String[]{"id", "income",};
        Cursor c = sqLiteDatabase.query("budgets", q, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            income = c.getInt(c.getColumnIndex("income"));
            c.close();
        }

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                amountLimit = expenses.get(position).getEstimate() - expenses.get(position).getExpense();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        if (!amountET.getText().toString().equals(""))
            if (expenses.get((int) spinnerCategories.getSelectedItemId()).getEstimate() + Integer.parseInt(amountET.getText().toString()) <= income - totalEstimate) {
                database.updateEstimate(expenses.get((int) spinnerCategories.getSelectedItemId()).getName(), expenses.get((int) spinnerCategories.getSelectedItemId()).getEstimate(), Integer.parseInt(amountET.getText().toString()));
                finish();
            }
    }
}
