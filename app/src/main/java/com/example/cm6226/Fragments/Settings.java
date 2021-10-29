package com.example.cm6226.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cm6226.MainActivity;
import com.example.cm6226.R;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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

        //Initialise view and mainActivity instance
        final MainActivity mainActivity = (MainActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //Retrieve button and textbox
        Button setNameButton = v.findViewById(R.id.btn_setname);
        final AppCompatEditText name = v.findViewById(R.id.tb_name);

        //When button is clicked, set global username to be equal to contents of tb_name
        setNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userName = name.getText().toString();
                Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_setname) + " " + name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieve button
        Button clearListButton = v.findViewById(R.id.btn_clearlist);

        //When button is clicked, show dialog. If answer is yes, clear global item list
        clearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mainActivity.listOfItems.clear();
                                Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_listcleared), Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getResources().getString(R.string.str_alert_listcleared)).setPositiveButton(getResources().getString(R.string.str_yes), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.str_no), dialogClickListener).show();
            }
        });

        //Retrieve button
        Button clearHistory = v.findViewById(R.id.btn_clearhistory);

        //When button is clicked, show dialog. If answer is yes, clear user global user history variables
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MainActivity.itemsAdded = 0;
                                MainActivity.itemsBought = 0;
                                MainActivity.totalQuantity = 0;
                                Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_historycleared), Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getResources().getString(R.string.str_alert_historycleared)).setPositiveButton(getResources().getString(R.string.str_yes), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.str_no), dialogClickListener).show();
            }
        });

        //Retrieve radio button
        RadioButton englishRB = v.findViewById(R.id.rb_english);

        //If radio button is clicked, set language to English
        englishRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setAppLocale("en");
            }
        });

        //Retrieve radio button
        RadioButton welshRB = v.findViewById(R.id.rb_welsh);

        //If radio button is clicked, set language to Welsh
        welshRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setAppLocale("wl");
            }
        });

        //Get current language and retrieve radio group
        String language = mainActivity.getLanguage();
        RadioGroup groupRB = v.findViewById(R.id.rg_languages);

        //Based on current language, show different radiobutton as ticked
        groupRB.clearCheck();
        if (language.equals("en") || language.equals("en_US")) {
            groupRB.check(englishRB.getId());
        } else if (language.equals("wl")) {
            groupRB.check(welshRB.getId());
        }

        //Retrieve button
        Button save = v.findViewById(R.id.btn_save);

        //When button is clicked, save global variables and global list
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.Save();
                };
            });

        //Retrieve button
        Button load = v.findViewById(R.id.btn_load);

        //When button is clicked, load previously saved variables and list
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.Load();
            };
        });

        return v;
    }
}