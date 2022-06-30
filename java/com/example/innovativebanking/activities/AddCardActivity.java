package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.CreditCardModel;
import com.example.innovativebanking.utils.Utils;

public class AddCardActivity extends AppCompatActivity {

    Spinner yearsArray;
    Spinner monthsArray;
    private EditText cardNumberTxt, cardPersonNameTxt, cvvNumberTxt;
    private Button addCard;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_add_card);
        findViews();
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        final Utils utils = new Utils(this);

        ArrayAdapter<CharSequence> yearsAdapter = ArrayAdapter.createFromResource(this, R.array.expiry_years_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(this, R.array.expiry_months_array, android.R.layout.simple_spinner_dropdown_item);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsArray.setAdapter(yearsAdapter);
        monthsArray.setAdapter(monthsAdapter);

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if (cardNumberTxt.getText().toString().length() != 16) {
                    valid = false;
                    Toast.makeText(AddCardActivity.this, "Invalid card number", Toast.LENGTH_SHORT).show();
                }
                if (cardPersonNameTxt.getText().toString().length() < 4) {
                    valid = false;
                    Toast.makeText(AddCardActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }
                if (!cardPersonNameTxt.getText().toString().matches("[a-zA-Z]+")) {
                    valid = false;
                    Toast.makeText(AddCardActivity.this, "The name should contain only letters", Toast.LENGTH_SHORT).show();
                }
                if (cvvNumberTxt.getText().toString().length() != 3) {
                    valid = false;
                    Toast.makeText(AddCardActivity.this, "CVV invalid", Toast.LENGTH_SHORT).show();
                }
                if (valid) {
                    StringBuilder date = new StringBuilder();
                    date.append(monthsArray.getSelectedItem().toString());
                    date.append("/");
                    date.append(yearsArray.getSelectedItem().toString());

                    String cardNumber = cardNumberTxt.getText().toString();

                    String cvvNumber = cvvNumberTxt.getText().toString();

                    appDatabase.creditCardDAO().addCreditCard(new CreditCardModel(Long.parseLong(cardNumber),cardPersonNameTxt.getText().toString(), Integer.parseInt(cvvNumber), date.toString(), utils.getCurrentUserId()));
                    emptyFields();
                    Intent intent = new Intent(getApplicationContext(),AddMoneyActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AddCardActivity.this, "Succes!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void findViews() {
        cardNumberTxt = findViewById(R.id.cardNumber);
        cardPersonNameTxt = findViewById(R.id.cardPersonName);
        cvvNumberTxt = findViewById(R.id.cvvNumber);
        yearsArray = findViewById(R.id.expiry_years_array);
        monthsArray = findViewById(R.id.expiry_days_array);
        addCard = findViewById(R.id.addNewCardBtn);
    }

    public void emptyFields() {
        cardPersonNameTxt.setText("");
        cardNumberTxt.setText("");
        cvvNumberTxt.setText("");
    }
}