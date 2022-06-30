package com.example.innovativebanking.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.innovativebanking.R;
import com.example.innovativebanking.activities.AddMoneyActivity;
import com.example.innovativebanking.activities.MainActivity;
import com.example.innovativebanking.activities.UpdateCardDetails;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.CreditCardModel;

import java.util.ArrayList;
import java.util.List;

public class CreditCardAdapter extends ArrayAdapter<CreditCardModel> {

    Context context;
    private ArrayList<CreditCardModel> dataSet;

    public CreditCardAdapter(Context context, ArrayList<CreditCardModel> dataSet) {
        super(context, R.layout.credit_card_lv_item, dataSet);
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public int getPosition(@Nullable CreditCardModel item) {
        int pos = 0;
        for(int i = 0; i < dataSet.size(); i++) {
            if(dataSet.get(i).equals(item)){
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public CreditCardModel getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CreditCardModel creditCardModel = getItem(position);
        final AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
//        View result;

        if (convertView == null) {
//            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.credit_card_lv_item, parent, false);
            viewHolder.cardPersonNameTxt = convertView.findViewById(R.id.cardPersonNameLvItem);
            viewHolder.cardNumberTxt = convertView.findViewById(R.id.cardNumberLvItem);
            viewHolder.cardIcon = convertView.findViewById(R.id.cardIcon);
            viewHolder.changeCardDetailsBtn = convertView.findViewById(R.id.changeCardDetailsBtn);
            viewHolder.deleteCard = convertView.findViewById(R.id.deleteCard);
            viewHolder.selectCardCheckBox = convertView.findViewById(R.id.cardCheckbox);

//            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//            result = convertView;
        }

        viewHolder.selectCardCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    creditCardModel.setCheckedForSupply(true);
                } else {
                    creditCardModel.setCheckedForSupply(false);
                }
            }
        });

        viewHolder.cardPersonNameTxt.setText(creditCardModel.getCardPersonName());
        viewHolder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase.creditCardDAO().deleteCreditCard(creditCardModel);
                Intent intent = new Intent(getContext(), MainActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.changeCardDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int creditCardId = creditCardModel.getCreditCardId();
                String creditId = Integer.toString(creditCardId);
                Intent intent = new Intent(getContext(), UpdateCardDetails.class);
                intent.putExtra("creditCardId",creditId);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        String creditNumber = Long.toString(creditCardModel.getCardNumber());
        StringBuffer stringBuffer = new StringBuffer(creditNumber);
        int pos = 4;
        for (int i = 0; i < 3; i++) {
            stringBuffer.insert(pos, '-');
            pos += 5;
        }
        viewHolder.cardNumberTxt.setText(stringBuffer);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView cardPersonNameTxt;
        TextView cardNumberTxt;
        Button changeCardDetailsBtn, deleteCard;
        CheckBox selectCardCheckBox;
        ImageView cardIcon;
    }

    public List<CreditCardModel> getDataSet() {
        return dataSet;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
