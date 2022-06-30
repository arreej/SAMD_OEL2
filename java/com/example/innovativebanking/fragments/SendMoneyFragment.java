package com.example.innovativebanking.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.innovativebanking.R;
import com.example.innovativebanking.activities.MainActivity;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.PartnerModel;
import com.example.innovativebanking.models.TransactionModel;
import com.example.innovativebanking.models.UserModel;
import com.example.innovativebanking.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendMoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMoneyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText nameInput;
    private EditText valueInput;
    private EditText accountInput;
    private CheckBox isPartenerInput;
    private Button submitSend;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SendMoneyFragment() {
    }

    public static SendMoneyFragment newInstance(String param1, String param2) {
        SendMoneyFragment fragment = new SendMoneyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getParentFragmentManager().setFragmentResultListener("partnerKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                nameInput.setText(result.getString("nameKey"));
                accountInput.setText(result.getString("accountKey"));
            }
        });
        final AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_send_money, container, false);
        getActivity().findViewById(R.id.addManually).setVisibility(View.INVISIBLE);
        getInputFields(v);

        submitSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                Utils utils = new Utils(getContext());
                UserModel currUser = appDatabase.userDAO().getUserById(utils.getCurrentUserId());

                if (nameInput.getText().toString().length() < 4) {
                    valid = false;
                    Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
                }
                if (!nameInput.getText().toString().matches("[a-zA-Z]+")) {
                    valid = false;
                    Toast.makeText(getActivity(), "The name should contain only letters", Toast.LENGTH_SHORT).show();
                }
                if (valueInput.getText().toString().equals("")) {
                    valid = false;
                    Toast.makeText(getActivity(), "Enter a value", Toast.LENGTH_SHORT).show();
                }
                if (accountInput.getText().toString().equals("")) {
                    valid = false;
                    Toast.makeText(getActivity(), "Enter an account", Toast.LENGTH_SHORT).show();
                }
                if(currUser.getBalance() < Float.parseFloat(valueInput.getText().toString())){
                    valid = false;
                    Toast.makeText(getActivity(), "You dont have enough money", Toast.LENGTH_SHORT).show();
                }
                if (valid) {
                    List<PartnerModel> partnerModels = appDatabase.partnerDAO().getAllPartners(utils.getCurrentUserId());
                    PartnerModel partnerModel = new PartnerModel(nameInput.getText().toString(), accountInput.getText().toString(), utils.getCurrentUserId());
                    appDatabase.transactionDAO().insertTransaction(new TransactionModel(Integer.parseInt(valueInput.getText().toString()), new Date().toString(), nameInput.getText().toString(), utils.getCurrentUserId(), true));
                    float moneyToBeSend = Float.parseFloat(valueInput.getText().toString());
                    float finalBalance = currUser.getBalance() - moneyToBeSend;
                    currUser.setBalance(finalBalance);
                    appDatabase.userDAO().updateUser(currUser);
                    int contor = 0;
                    if (isPartenerInput.isChecked()) {
                        for (PartnerModel model : partnerModels) {
                            if (model.getAccount().equals(partnerModel.getAccount())) {
                                contor++;
                            }
                        }
                        if (contor >= 1) {
                            Toast.makeText(getActivity(), "Already saved", Toast.LENGTH_SHORT).show();
                        } else {
                            appDatabase.partnerDAO().addPartner(partnerModel);
                        }
                    }
                    Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
                    nameInput.setText("");
                    accountInput.setText("");
                    valueInput.setText("");
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    ((Activity)getContext()).finish();
                }
            }
        });
        return v;

    }

    public void getInputFields(View v) {
        submitSend = v.findViewById(R.id.sendMoneyBtn);
        nameInput = v.findViewById(R.id.nameInput);
        valueInput = v.findViewById(R.id.valueInput);
        accountInput = v.findViewById(R.id.accountInput);
        isPartenerInput = v.findViewById(R.id.isPartener);
    }

}