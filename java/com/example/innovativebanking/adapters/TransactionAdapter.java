package com.example.innovativebanking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.innovativebanking.R;
import com.example.innovativebanking.models.TransactionModel;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<TransactionModel> {

    Context context;
    List<TransactionModel> dataSet;

    public TransactionAdapter(Context context, List<TransactionModel> dataSet) {
        super(context, R.layout.transaction_main_item, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Nullable
    @Override
    public TransactionModel getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TransactionModel transactionModel = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.transaction_main_item, parent, false);
            viewHolder.transactionDateTxt = convertView.findViewById(R.id.transactionDate);
            viewHolder.transactionReceiverNameTxt = convertView.findViewById(R.id.transactionReceiverName);
            viewHolder.transactionValueTxt = convertView.findViewById(R.id.transactionValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.transactionReceiverNameTxt.setText(transactionModel.getName());
        viewHolder.transactionDateTxt.setText(transactionModel.getDate());
        StringBuilder transactionSign = new StringBuilder();
        if(transactionModel.isExpense()) {
            transactionSign.append("-");
        } else {
            transactionSign.append("+");
        }
        viewHolder.transactionValueTxt.setText(transactionSign.toString() + transactionModel.getMoney() + " RON");
        return convertView;
    }

    private static class ViewHolder {
        TextView transactionReceiverNameTxt, transactionValueTxt, transactionDateTxt;
    }
}
