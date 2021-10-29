package com.example.cm6226.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cm6226.R;

public class ItemFragment extends Fragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_QUANTITY = "quantity";
    private static final String ARG_ITEM = "item";

    private String mName;
    private String mQuantity;
    private String mItem;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(String param1, String param2, String param3) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, param1);
        args.putString(ARG_QUANTITY, param2);
        args.putString(ARG_ITEM, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mQuantity = getArguments().getString(ARG_QUANTITY);
            mItem = getArguments().getString(ARG_ITEM);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        //Set label to display item name
        AppCompatTextView textA = v.findViewById(R.id.text_a);
        textA.setText(getResources().getString(R.string.str_fraglabel1) + " " + mName);

        //Set label to display item quantity
        AppCompatTextView textB = v.findViewById(R.id.text_b);
        textB.setText(getResources().getString(R.string.str_fraglabel2) + " " + mQuantity);

        //Set label to display item
        AppCompatTextView textC = v.findViewById(R.id.text_c);
        textC.setText(getResources().getString(R.string.str_fraglabel3) + " " + mItem);

        return v;
    }
}