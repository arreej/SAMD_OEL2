package com.example.innovativebanking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.innovativebanking.R;
import com.example.innovativebanking.database.AppDatabase;
import com.example.innovativebanking.models.PartnerModel;
import com.example.innovativebanking.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartnerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnerListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentPartenerListener listener;
    private ListView partenerListView;
    private List<PartnerModel> parteners = new ArrayList<>();
    private String nameResult;
    private String accountResult;
    private String mParam1;
    private String mParam2;

    public PartnerListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartnerListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartnerListFragment newInstance(String param1, String param2) {
        PartnerListFragment fragment = new PartnerListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initMocks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_partner_list, container, false);
        getActivity().findViewById(R.id.addManually).setVisibility(View.VISIBLE);
        partenerListView = v.findViewById(R.id.partnerListView);
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        Utils utils = new Utils(getContext());
        List<PartnerModel> partnerModels = new ArrayList<>();
        partnerModels =  appDatabase.partnerDAO().getAllPartners(utils.getCurrentUserId());
        parteners.addAll(partnerModels);

        ArrayAdapter<PartnerModel> arrayAdapter = new ArrayAdapter<PartnerModel>(getActivity(), android.R.layout.simple_list_item_1, parteners) {
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                return view;
            }
        };
        partenerListView.setAdapter(arrayAdapter);
        partenerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartnerModel item = (PartnerModel) partenerListView.getItemAtPosition(position);
                Bundle result = new Bundle();
                result.putString("nameKey", item.getName());
                result.putString("accountKey", item.getAccount());
                getParentFragmentManager().setFragmentResult("partnerKey", result);
                SendMoneyFragment sendMoneyFragment = new SendMoneyFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.partnerList, sendMoneyFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return v;
    }

    public void initMocks() {
        PartnerModel partnerModel1 = new PartnerModel("Eftene Robert", "RO123BRD", 1);
        PartnerModel partnerModel2 = new PartnerModel("Eftene Andrei", "RO222BCR", 1);
        PartnerModel partnerModel3 = new PartnerModel("John Doe", "RO666REVOLUT", 1);
        parteners.add(partnerModel1);
        parteners.add(partnerModel2);
        parteners.add(partnerModel3);
    }

    public interface FragmentPartenerListener {
        void onInputPartenerSent(CharSequence input);
    }

}