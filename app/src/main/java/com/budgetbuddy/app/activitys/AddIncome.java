package com.budgetbuddy.app.activitys;
//Author: Burcu İÇEN

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.databases.Budgets;

public class AddIncome extends Activity {

    private EditText amountET;
    private Budgets database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_addincome);
        super.onCreate(savedInstanceState);

        amountET = findViewById(R.id.addincome_amount);
        database = new Budgets(this);
    }

    public void save(View view) {
        if (!amountET.getText().toString().equals("")) {
            SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
            String[] query = new String[]{"id", "income"};
            Cursor cursor = sqLiteDatabase.query("budgets", query, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                database.updateIncome(cursor.getString(0), cursor.getInt(1), Integer.parseInt(amountET.getText().toString()));
                cursor.close();
                finish();
            }
        }
    }

    public void cancel(View view) {
        finish();
    }
}