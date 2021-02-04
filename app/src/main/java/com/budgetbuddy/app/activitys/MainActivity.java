package com.budgetbuddy.app.activitys;
//Author: Burcu İÇEN, Çağrıhan GÜNAY

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.adapters.ExpenseAdapter;
import com.budgetbuddy.app.databases.Budgets;
import com.budgetbuddy.app.models.Expense;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class MainActivity extends Activity {

    private PieChart chart;
    private TextView total, left, ofincomespent;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Expense> expenses;
    private Budgets database;
    private ExpenseAdapter adapter;

    private int totalExpense, leftIncome, totalIncome, totalEstimate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_main);
        super.onCreate(savedInstanceState);

        database = new Budgets(this);

        chart = findViewById(R.id.main_chart);

        bottomNavigationView = findViewById(R.id.main_bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main_bnv_menu_addexpense) {
                    startActivity(new Intent(MainActivity.this, AddExpense.class));
                    return true;
                } else if (item.getItemId() == R.id.main_bnv_menu_addincome) {
                    startActivity(new Intent(MainActivity.this, AddIncome.class));
                    return true;
                }  else if (item.getItemId() == R.id.main_bnv_menu_addestimate) {
                    startActivity(new Intent(MainActivity.this, AddEstimate.class));
                    return true;
                }else {
                    return false;
                }
            }
        });

        total = findViewById(R.id.main_total);
        left = findViewById(R.id.main_left);
        ofincomespent = findViewById(R.id.main_ofincomespent);

        expenses = new ArrayList<>();
        recyclerView = findViewById(R.id.main_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ExpenseAdapter(this, expenses);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        totalExpense = 0;
        leftIncome = 0;
        totalIncome = 0;
        totalEstimate = 0;

        getBudget();
    }

    private void getBudget() {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String[] query = new String[]{"name", "estimate", "expense"};
        Cursor cursor = sqLiteDatabase.query("expenses", query, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            expenses.clear();
            for (int i = 0; i < cursor.getCount(); i++) {
                expenses.add(new Expense(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("estimate")), cursor.getInt(cursor.getColumnIndex("expense"))));

                totalExpense = totalExpense + cursor.getInt(cursor.getColumnIndex("expense"));
                totalEstimate = totalEstimate + cursor.getInt(cursor.getColumnIndex("estimate"));

                cursor.moveToNext();
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }

        String[] q = new String[]{"id", "income", "date"};
        Cursor c = sqLiteDatabase.query("budgets", q, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();

            totalIncome = c.getInt(1);

            total.setText(String.valueOf(totalIncome));
            left.setText(String.valueOf(totalIncome - totalExpense));

            setChart(totalExpense, totalEstimate - totalExpense);
        }
        c.close();

        ofincomespent.setText(new StringBuilder().append(Float.valueOf((totalExpense * 100) / totalIncome)).append(getString(R.string.main_ofincomespent)).toString());

        database.close();
    }

    private void setChart(float... f) {
        List<PieEntry> pieEntries = new ArrayList<>();

        if (f[0] != 0)
            pieEntries.add(new PieEntry(f[0], "Expense"));
        else
            pieEntries.add(new PieEntry(0.001f, "Expense"));
        pieEntries.add(new PieEntry(f[1], "Left"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        chart.setHoleRadius(0);
        chart.setTransparentCircleRadius(0);
        chart.setData(pieData);

    }
}
