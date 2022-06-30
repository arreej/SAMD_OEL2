package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.UserModel;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    public static final Pattern PASSWORD = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");
    private EditText firstNameTxt, lastNameTxt, phoneTxt, emailTxt, passTxt;
    private DatePicker birthday;
    private Button submitRegisterBtn;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_register);
        final AppDatabase appDatabase = AppDatabase.getInstance(this);

        findViews();
        submitRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true;
                if (firstNameTxt.length() < 2) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "First name should be longer", Toast.LENGTH_SHORT).show();
                }
                if (lastNameTxt.length() < 2) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "Last name should be longer", Toast.LENGTH_SHORT).show();
                }
                if (!firstNameTxt.getText().toString().matches("[a-zA-Z]+") || !lastNameTxt.getText().toString().matches("[a-zA-Z]+")) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "The name should contain only letters", Toast.LENGTH_SHORT).show();
                }
                if (phoneTxt.getText().length() != 10) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "The phone number should have 10 digits", Toast.LENGTH_SHORT).show();
                }
                if (!emailTxt.getText().toString().matches("^(.+)@(.+)$")) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "The email is invalid", Toast.LENGTH_SHORT).show();
                }
                if (!PASSWORD.matcher(passTxt.getText().toString()).matches()) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "The password should have at leas 1 digit, 1 lower case, 1 upper case, one special character and no white spaces", Toast.LENGTH_LONG).show();
                }
                if (birthday.getYear() > 2006) {
                    valid = false;
                    Toast.makeText(RegisterActivity.this, "You should be at least 14 years older to create an account", Toast.LENGTH_SHORT).show();
                }

                if (valid == true) {
                    StringBuilder birthdayUser = new StringBuilder();
                    birthdayUser.append(birthday.getDayOfMonth());
                    birthdayUser.append("/");
                    birthdayUser.append(birthday.getMonth());
                    birthdayUser.append("/");
                    birthdayUser.append(birthday.getYear());
                    int phoneNo = Integer.parseInt(phoneTxt.getText().toString());
                    UserModel userModel = new UserModel(1, firstNameTxt.getText().toString(), lastNameTxt.getText().toString(), emailTxt.getText().toString(), phoneNo, passTxt.getText().toString(), birthdayUser.toString(), 1000);
                    appDatabase.userDAO().insertUser(userModel);
                    emptyFields();
                    Toast.makeText(RegisterActivity.this, "Everything is good, you can login now", Toast.LENGTH_SHORT).show();
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
        birthday = findViewById(R.id.birthday);
        birthday.setMaxDate(1609629813000L);
        submitRegisterBtn = findViewById(R.id.createAccount);
    }

    public void emptyFields() {
        firstNameTxt.setText("");
        lastNameTxt.setText("");
        phoneTxt.setText("");
        emailTxt.setText("");
        passTxt.setText("");
    }
}