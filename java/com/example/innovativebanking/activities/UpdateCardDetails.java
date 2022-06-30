package com.example.innovativebanking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.CreditCardModel;
import com.example.innovativebanking.utils.Utils;

public class UpdateCardDetails extends AppCompatActivity {

    Spinner yearsArray;
    Spinner monthsArray;
    private EditText cardNumberTxt, cardPersonNameTxt, cvvNumberTxt;
    private Button updateCard;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_update_card_details);
        findViews();
        final Utils utils = new Utils(this);
        String creditCardId = getIntent().getStringExtra("creditCardId");
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        int creditId = Integer.parseInt(creditCardId);
        final CreditCardModel creditCardModel =  appDatabase.creditCardDAO().getCreditCardById(creditId);

        cardNumberTxt.setText(Long.toString(creditCardModel.getCardNumber()));
        cardPersonNameTxt.setText(creditCardModel.getCardPersonName());
        cvvNumberTxt.setText(Integer.toString(creditCardModel.getCardCVV()));

        String expiryDate = creditCardModel.getCardExpiryDate();
        String str[] = expiryDate.split("/");

        ArrayAdapter<CharSequence> yearsAdapter = ArrayAdapter.createFromResource(this, R.array.expiry_years_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(this, R.array.expiry_months_array, android.R.layout.simple_spinner_dropdown_item);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsArray.setAdapter(yearsAdapter);
        monthsArray.setAdapter(monthsAdapter);


        int pos = 0;
        for(int i = 0; i < yearsAdapter.getCount(); i++) {
            if(str[1].equals(yearsAdapter.getItem(i).toString())) {
                pos = i;
            }
        }
        yearsArray.setSelection(pos);

        pos = 0;
        for(int i = 0; i < monthsAdapter.getCount(); i++) {
            if(str[0].equals(monthsAdapter.getItem(i).toString())) {
               pos = i;
            }
        }
        monthsArray.setSelection(pos);

        updateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if (cardNumberTxt.getText().toString().length() != 16) {
                    valid = false;
                    Toast.makeText(UpdateCardDetails.this, "Invalid card number", Toast.LENGTH_SHORT).show();
                }
                if (cardPersonNameTxt.getText().toString().length() < 4) {
                    valid = false;
                    Toast.makeText(UpdateCardDetails.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }
                if (!cardPersonNameTxt.getText().toString().matches("[a-zA-Z]+")) {
                    valid = false;
                    Toast.makeText(UpdateCardDetails.this, "The name should contain only letters", Toast.LENGTH_SHORT).show();
                }
                if (cvvNumberTxt.getText().toString().length() != 3) {
                    valid = false;
                    Toast.makeText(UpdateCardDetails.this, "CVV invalid", Toast.LENGTH_SHORT).show();
                }
                if (valid) {
                    StringBuilder date = new StringBuilder();
                    date.append(monthsArray.getSelectedItem().toString());
                    date.append("/");
                    date.append(yearsArray.getSelectedItem().toString());

                    String cardNumber = cardNumberTxt.getText().toString();

                    String cvvNumber = cvvNumberTxt.getText().toString();
                    creditCardModel.setCardPersonName(cardPersonNameTxt.getText().toString());
                    creditCardModel.setCardCVV(Integer.parseInt(cvvNumber));
                    creditCardModel.setCardNumber(Long.parseLong(cardNumber));
                    creditCardModel.setCardExpiryDate(date.toString());
                    appDatabase.creditCardDAO().updateCreditCard(creditCardModel);
                    emptyFields();
                    Intent intent = new Intent(getApplicationContext(),AddMoneyActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(UpdateCardDetails.this, "Succes!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void findViews() {
        cardNumberTxt = findViewById(R.id.cardNumber);
        cardPersonNameTxt = findViewById(R.id.cardPersonName);
        cvvNumberTxt = findViewById(R.id.cvvNumber);
        yearsArray = findViewById(R.id.expiry_years_array_update);
        monthsArray = findViewById(R.id.expiry_months_array_update);
        updateCard = findViewById(R.id.updateCardDetails);
    }

    public void emptyFields() {
        cardPersonNameTxt.setText("");
        cardNumberTxt.setText("");
        cvvNumberTxt.setText("");
    }
}