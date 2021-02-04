package com.budgetbuddy.app.activitys;
//Author: Çağrıhan GÜNAY

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.budgetbuddy.app.R;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_launch);
        super.onCreate(savedInstanceState);
    }

    public void createYourBudget(View view) {
        startActivity(new Intent(this, CreateBudgetActivity.class));
        finish();
    }
}
