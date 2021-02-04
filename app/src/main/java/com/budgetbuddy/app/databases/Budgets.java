package com.budgetbuddy.app.databases;
//Author: Burcu İÇEN, Çağrıhan GÜNAY

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Budgets extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "budgets";
    public static final int VERSION = 1;

    public Budgets(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE budgets (id TEXT, income INTEGER, date TEXT);");
        db.execSQL("CREATE TABLE expenses (name TEXT, estimate INTEGER, expense INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean updateExpense(String category, Integer amount, Integer addamount) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("expense", amount + addamount);

        sqLiteDatabase.update("expenses", contentValues, "name = ?", new String[]{category});
        return true;
    }

    public boolean updateIncome(String id, Integer income, Integer addincome) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("income", income + addincome);

        sqLiteDatabase.update("budgets", contentValues, "id = ?", new String[]{id});
        return true;
    }

    public boolean updateEstimate(String category, Integer amount, Integer addamount) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("estimate", amount + addamount);

        sqLiteDatabase.update("expenses", contentValues, "name = ?", new String[]{category});
        return true;
    }
}
