package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.database.UserTransactions;
import com.example.innovativebanking.models.TransactionModel;
import com.example.innovativebanking.utils.PieChartView;
import com.example.innovativebanking.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    LinearLayout graphLayout;
    TextView expensesTxt, incomesTxt;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_statistics);
        incomesTxt = findViewById(R.id.incomesTxt);
        expensesTxt = findViewById(R.id.expensesTxt);
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        Utils utils = new Utils(this);
        List<UserTransactions> userTransactionsList = appDatabase.userDAO().getAllTransactionsByUserId();
        List<TransactionModel> allTransactions = new ArrayList<>();
        for (UserTransactions userTransactions : userTransactionsList) {
            if (userTransactions.userModel.getUserId() == utils.getCurrentUserId()) {
                allTransactions.addAll(userTransactions.transactionModelList);
            }
        }
        float expenses = 0;
        float incomes = 0;
        for (TransactionModel transaction : allTransactions) {
            if (transaction.isExpense()) {
                expenses += transaction.getMoney();
            } else {
                incomes += transaction.getMoney();
            }
        }

        incomesTxt.setText("Incomes: " + incomes + " Rupees");
        expensesTxt.setText("Expenses: " + expenses + " Rupees");

        float values[] = {incomes, expenses};
        graphLayout = findViewById(R.id.graphLayout);
        graphLayout.addView(new PieChartView(this, calculatePieChartData(values)));
        PieChartView p = new PieChartView(this, values);
    }

    private float[] calculatePieChartData(float[] values) {
        float valuesSum = 0;
        float[] pieValuesPercentage = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            valuesSum += values[i];
        }
        for (int i = 0; i < values.length; i++) {
            pieValuesPercentage[i] = 360 * (values[i] / valuesSum);
        }
        return pieValuesPercentage;
    }
}