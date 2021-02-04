package com.budgetbuddy.app.activitys;
//Author: Burcu İÇEN

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.budgetbuddy.app.databases.Budgets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Splash extends Activity {

    private Budgets budgets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgets = new Budgets(this);

        SQLiteDatabase sqLiteDatabase = budgets.getWritableDatabase();
        String[] q = new String[]{"income", "date"};
        Cursor c = sqLiteDatabase.query("budgets", q, null, null, null, null, null, null);
        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(c.getString(c.getColumnIndex("date")));
                Date date = null;
                try {
                    date = simpleDateFormat.parse(c.getString(c.getColumnIndex("date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, 1);

                if (calendar.getTime().getTime() > Calendar.getInstance().getTime().getTime())
                    startActivity(new Intent(this, MainActivity.class));
                else
                    startActivity(new Intent(this, LaunchActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LaunchActivity.class));
            }
            finish();
        }
        c.close();
    }
}
