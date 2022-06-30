package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innovativebanking.R;
import com.example.innovativebanking.adapters.CompanyStockAdapter;
import com.example.innovativebanking.utils.CompanyModel;
import com.example.innovativebanking.utils.DownloadJSONAsyncTask;
import com.example.innovativebanking.utils.StockModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class InvestActivity extends AppCompatActivity {

    ArrayList<CompanyModel> companyModelArrayList = new ArrayList<>();
    ListView companyStockListView;
    private ProgressBar progressBar;
    private TextView stockPeriod;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_invest);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        companyStockListView = findViewById(R.id.companyStockLv);
        stockPeriod = findViewById(R.id.periodTimeTxt);

        DownloadJSONAsyncTask downloadJSONAsyncTask = new DownloadJSONAsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int year = jsonObject.optInt("year");
                    String month = jsonObject.optString("month");
                    JSONArray jsonArrayCompanies = jsonObject.getJSONArray("companies");
                    for (int i = 0; i < jsonArrayCompanies.length(); i++) {
                        JSONObject jsonCompany = jsonArrayCompanies.getJSONObject(i);
                        String companyName = jsonCompany.optString("companyName");
                        String companyCeo = jsonCompany.optString("companyCeo");
                        String companyDescription = jsonCompany.optString("companyDescription");
                        JSONObject jsonCompanyStock = jsonCompany.getJSONObject("companyStock");
                        String stockSymbol = jsonCompanyStock.optString("stockSymbol");
                        int initialPrice = jsonCompanyStock.optInt("initialPrice");
                        int currentPrince = jsonCompanyStock.optInt("currentPrice");

                        CompanyModel companyModel = new CompanyModel(companyCeo, companyName, new StockModel(stockSymbol, initialPrice, currentPrince), companyDescription);
                        companyModelArrayList.add(companyModel);

                    }
                    CompanyStockAdapter companyStockAdapter = new CompanyStockAdapter(getApplicationContext(), companyModelArrayList);
                    companyStockListView.setAdapter(companyStockAdapter);
                    stockPeriod.setText("Traded in " + month + " " + year);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        downloadJSONAsyncTask.execute("https://api.mocki.io/v1/68a8f339");


    }
}