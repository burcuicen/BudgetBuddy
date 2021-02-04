package com.budgetbuddy.app.adapters;
//Author: Çağrıhan GÜNAY

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.models.Expense;

import java.util.ArrayList;

public class CategoryEntryAdapter extends RecyclerView.Adapter<CategoryEntryAdapter.ViewHolder> {
    private ArrayList<Expense> expenses;

    public CategoryEntryAdapter(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_categoryentry, viewGroup, false);
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
        private final TextView textView;
        private final EditText editText;

        public ViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.item_categoryentry_tv);
            editText = view.findViewById(R.id.item_categoryentry_et);
        }

        public void setData(Expense expense, int position) {
            textView.setText(expense.getName());
            editText.setHint("0");

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    expenses.set(position, new Expense(expense.getName(), editText.getText().toString().equals("") ? null : Integer.parseInt(editText.getText().toString()), expense.getExpense()));
                }
            });
        }
    }
}
