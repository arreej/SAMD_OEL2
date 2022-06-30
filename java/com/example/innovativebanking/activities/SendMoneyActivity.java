package com.example.innovativebanking.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.innovativebanking.R;
import com.example.innovativebanking.fragments.PartnerListFragment;
import com.example.innovativebanking.fragments.SendMoneyFragment;
import com.example.innovativebanking.models.PartnerModel;

import java.util.ArrayList;
import java.util.List;


public class SendMoneyActivity extends AppCompatActivity {

    private Button sendManually;
    private SendMoneyFragment sendMoneyFragment = new SendMoneyFragment();
    private PartnerListFragment partnerListFragment = new PartnerListFragment();
    private List<PartnerModel> parteners = new ArrayList<>();

    @SuppressLint({"WrongConstant", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_send_money);
        initMocks();
        sendManually = findViewById(R.id.addManually);
        if (findViewById(R.id.partnerList) != null || findViewById(R.id.sendForm) != null) {
            if (savedInstanceState != null) {
                return;
            }

            partnerListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.partnerList, partnerListFragment).commit();

            sendManually.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.fragment_close_enter, R.anim.fragment_close_exit);
                    transaction.replace(R.id.partnerList, sendMoneyFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }


    }

    public void initMocks() {
        PartnerModel partnerModel1 = new PartnerModel("Eftene Robert", "RO123BRD", 1);
        PartnerModel partnerModel2 = new PartnerModel("Eftene Andrei", "RO222BCR", 1);
        PartnerModel partnerModel3 = new PartnerModel("John Doe", "RO666REVOLUT", 1);
        parteners.add(partnerModel1);
        parteners.add(partnerModel2);
        parteners.add(partnerModel3);
    }


}