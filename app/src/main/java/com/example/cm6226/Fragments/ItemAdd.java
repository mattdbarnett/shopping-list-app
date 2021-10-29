package com.example.cm6226.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cm6226.MainActivity;
import com.example.cm6226.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemAdd extends Fragment {

    public ItemAdd() {
        // Required empty public constructor
    }

    public static ItemAdd newInstance() {
        ItemAdd fragment = new ItemAdd();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_itemadd, container, false);
        AppCompatButton button = v.findViewById(R.id.btn_submit);
        final AppCompatEditText name = v.findViewById(R.id.tb_itemname);
        final AppCompatEditText quantity = v.findViewById(R.id.tb_quantity);

        //When submit button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                //Call itemAdd with field contents
                mainActivity.itemAdd(name.getText().toString(), quantity.getText().toString());

                //Reset fields
                name.setText("");
                quantity.setText("1");
            }
        });
        return v;
    }
}