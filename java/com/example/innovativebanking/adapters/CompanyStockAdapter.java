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
import com.example.innovativebanking.utils.CompanyModel;

import java.util.ArrayList;

public class CompanyStockAdapter extends ArrayAdapter<CompanyModel> {
    Context context;
    ArrayList<CompanyModel> dataSet;

    public CompanyStockAdapter(Context context, ArrayList<CompanyModel> data) {
        super(context, R.layout.company_stock_item, data);
        this.context = context;
        this.dataSet = data;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Nullable
    @Override
    public CompanyModel getItem(int position) {
        return dataSet.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CompanyModel companyModel = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.company_stock_item, parent, false);
            viewHolder.companyDescriptionTxt = convertView.findViewById(R.id.companyDescriptionTxt);
            viewHolder.companyNameTxt = convertView.findViewById(R.id.companyNameTxt);
            viewHolder.companySymbolTxt = convertView.findViewById(R.id.stockSymbolTxt);
            viewHolder.stockCurrentPriceTxt = convertView.findViewById(R.id.stockCurrentPriceTxt);
            viewHolder.stockInitialPriceTxt = convertView.findViewById(R.id.stockInitialPriceTxt);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.companyNameTxt.setText(companyModel.getCompanyName());
        viewHolder.companyDescriptionTxt.setText(companyModel.getCompanyDescription());
        viewHolder.companySymbolTxt.setText(companyModel.getCompanyStock().getStockSymbol());
        viewHolder.stockCurrentPriceTxt.setText(Integer.toString(companyModel.getCompanyStock().getCurrentPrice()) + "$");
        viewHolder.stockInitialPriceTxt.setText("Initial stock value: " + Integer.toString(companyModel.getCompanyStock().getInitialPrice()) + "$");

        return convertView;
    }

    private static class ViewHolder {
        TextView companyNameTxt, companySymbolTxt, companyDescriptionTxt, stockCurrentPriceTxt, stockInitialPriceTxt;
    }
}
