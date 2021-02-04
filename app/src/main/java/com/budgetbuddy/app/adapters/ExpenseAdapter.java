package com.budgetbuddy.app.adapters;
//Author: Burcu İÇEN

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.models.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private ArrayList<Expense> expenses;
    private Context context;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expense, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setData(expenses.get(i), i);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView left;

        public ViewHolder(View view) {
            super(view);

            icon = view.findViewById(R.id.expense_icon);
            name = view.findViewById(R.id.expense_name);
            left = view.findViewById(R.id.expense_left);
        }

        public void setData(Expense expense, int position) {
            name.setText(expense.getName());
            left.setText(String.valueOf(expense.getEstimate() - expense.getExpense()));

            if (context.getString(R.string.createbucket_shopping).equals(expense.getName())) {
                icon.setImageResource(R.drawable.shopping);
            } else if (context.getString(R.string.createbucket_payments).equals(expense.getName())) {
                icon.setImageResource(R.drawable.payments);
            } else if (context.getString(R.string.createbucket_education).equals(expense.getName())) {
                icon.setImageResource(R.drawable.education);
            } else if (context.getString(R.string.createbucket_kitchen).equals(expense.getName())) {
                icon.setImageResource(R.drawable.kitchen);
            } else if (context.getString(R.string.createbucket_bills).equals(expense.getName())) {
                icon.setImageResource(R.drawable.bills);
            } else if (context.getString(R.string.createbucket_debts).equals(expense.getName())) {
                icon.setImageResource(R.drawable.debts);
            } else if (context.getString(R.string.createbucket_entertainment).equals(expense.getName())) {
                icon.setImageResource(R.drawable.entertainment);
            } else if (context.getString(R.string.createbucket_investment).equals(expense.getName())) {
                icon.setImageResource(R.drawable.investment);
            } else if (context.getString(R.string.createbucket_rent_mortgage).equals(expense.getName())) {
                icon.setImageResource(R.drawable.rent);
            } else if (context.getString(R.string.createbucket_transportation).equals(expense.getName())) {
                icon.setImageResource(R.drawable.transportation);
            } else {
                icon.setImageResource(R.drawable.money);
            }
        }
    }
}
