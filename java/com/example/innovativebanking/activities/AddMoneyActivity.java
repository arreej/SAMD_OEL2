package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innovativebanking.R;
import com.example.innovativebanking.adapters.CreditCardAdapter;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.database.UserCreditCards;
import com.example.innovativebanking.models.CreditCardModel;
import com.example.innovativebanking.models.TransactionModel;
import com.example.innovativebanking.models.UserModel;
import com.example.innovativebanking.utils.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMoneyActivity extends AppCompatActivity {

    private Button addCard, addMoney, saveCardDetails;
    ArrayList<CreditCardModel> creditCardModelList;
    ListView creditCardsListView;
    private EditText moneyToBeAdded;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_add_money);
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        final Utils utils = new Utils(this);
        creditCardModelList = new ArrayList<>();

        List<UserCreditCards> userCreditCardsList = appDatabase.userDAO().getAllCreditCardsByUserId();
        for (UserCreditCards userCreditCards : userCreditCardsList) {
            if(userCreditCards.userModel.getUserId() == utils.getCurrentUserId()) {
                creditCardModelList.addAll(userCreditCards.creditCardModelList);
            }
        }

        creditCardsListView = findViewById(R.id.savedCreditCardsLv);

        final CreditCardAdapter creditCardAdapter = new CreditCardAdapter(this, creditCardModelList);
        creditCardsListView.setAdapter(creditCardAdapter);
        addCard = findViewById(R.id.addNewCard);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    Intent intent = new Intent(AddMoneyActivity.this, AddCardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        moneyToBeAdded = findViewById(R.id.amountToBeAddedTxt);
        addMoney = findViewById(R.id.addAmountOfMoney);
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if(moneyToBeAdded.getText().toString().length() < 2) {
                    valid = false;
                    Toast.makeText(AddMoneyActivity.this, "You must enter an amount bigger then 50RON", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(moneyToBeAdded.getText().toString()) < 50) {
                        valid = false;
                        Toast.makeText(AddMoneyActivity.this, "You must enter an amount bigger then 50RON", Toast.LENGTH_SHORT).show();
                    }
                }
                int contor=0;
                int pos = 0;
                for(int i=0; i < creditCardModelList.size(); i++) {
                    if(creditCardModelList.get(i).isCheckedForSupply()) {
                        contor++;
                        pos = i;
                    }
                }
                if(contor > 1 || contor < 1) {
                    valid = false;
                    Toast.makeText(AddMoneyActivity.this, "You must select one card", Toast.LENGTH_SHORT).show();
                }

                if(valid) {
                    UserModel userModel = appDatabase.userDAO().getUserById(utils.getCurrentUserId());
                    float previosBalance = userModel.getBalance();
                    float moneyAdded = Float.parseFloat(moneyToBeAdded.getText().toString());
                    float finalSum = previosBalance + moneyAdded;
                    userModel.setBalance(finalSum);
                     CreditCardModel selectedCard = creditCardModelList.get(pos);
                    appDatabase.transactionDAO().insertTransaction(new TransactionModel(moneyAdded,new Date().toString(),selectedCard.getCardPersonName(),utils.getCurrentUserId(), false));
                    appDatabase.userDAO().updateUser(userModel);
                    Intent intent = new Intent(AddMoneyActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        saveCardDetails = findViewById(R.id.saveCardDetails);
        saveCardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(AddMoneyActivity.this.getFilesDir(),"text");
                if(!file.exists()) {
                    file.mkdir();
                }
                try {
                    File cardsReport = new File(file,"cardsDetailsReport.csv");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(cardsReport));
                    for (CreditCardModel creditCardModel : creditCardModelList) {
                        writer.write(creditCardModel.toString());
                        writer.write("\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}