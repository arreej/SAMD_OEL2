package com.example.innovativebanking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.UserModel;
import com.example.innovativebanking.utils.Utils;

import java.util.regex.Pattern;

public class UpdateUserDetails extends AppCompatActivity {

    public static final Pattern PASSWORD = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");
    private EditText firstNameTxt, lastNameTxt, phoneTxt, emailTxt, passTxt;
    private Button updateAccountBtn;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_update_user_details);
        findViews();
        Utils utils = new Utils(this);
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        final UserModel userModel = appDatabase.userDAO().getUserById(utils.getCurrentUserId());
        firstNameTxt.setText(userModel.getFirstName());
        lastNameTxt.setText(userModel.getLastName());

        phoneTxt.setText("0" + Integer.toString(userModel.getPhone()));
        emailTxt.setText(userModel.getEmail());
        passTxt.setText(userModel.getPassword());

        updateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true;
                if (firstNameTxt.length() < 2) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "First name should be longer", Toast.LENGTH_SHORT).show();
                }
                if (lastNameTxt.length() < 2) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "Last name should be longer", Toast.LENGTH_SHORT).show();
                }
                if (!firstNameTxt.getText().toString().matches("[a-zA-Z]+") || !lastNameTxt.getText().toString().matches("[a-zA-Z]+")) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "The name should contain only letters", Toast.LENGTH_SHORT).show();
                }
                if (phoneTxt.getText().length() != 10) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "The phone number should have 10 digits", Toast.LENGTH_SHORT).show();
                }
                if (!emailTxt.getText().toString().matches("^(.+)@(.+)$")) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "The email is invalid", Toast.LENGTH_SHORT).show();
                }
                if (!PASSWORD.matcher(passTxt.getText().toString()).matches()) {
                    valid = false;
                    Toast.makeText(UpdateUserDetails.this, "The password should have at leas 1 digit, 1 lower case, 1 upper case, one special character and no white spaces", Toast.LENGTH_LONG).show();
                }

                if (valid == true) {
                    userModel.setFirstName(firstNameTxt.getText().toString());
                    userModel.setLastName(lastNameTxt.getText().toString());
                    userModel.setEmail(emailTxt.getText().toString());
                    userModel.setPassword(passTxt.getText().toString());
                    userModel.setPhone(Integer.parseInt(phoneTxt.getText().toString()));
                    appDatabase.userDAO().updateUser(userModel);
                    Intent intent = new Intent(UpdateUserDetails.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    emptyFields();
                    Toast.makeText(UpdateUserDetails.this, "Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void findViews() {
        firstNameTxt = findViewById(R.id.firstName);
        lastNameTxt = findViewById(R.id.lastName);
        phoneTxt = findViewById(R.id.phone);
        emailTxt = findViewById(R.id.mail);
        passTxt = findViewById(R.id.password);
        updateAccountBtn = findViewById(R.id.updateAccount);
    }

    public void emptyFields() {
        firstNameTxt.setText("");
        lastNameTxt.setText("");
        phoneTxt.setText("");
        emailTxt.setText("");
        passTxt.setText("");
    }
}