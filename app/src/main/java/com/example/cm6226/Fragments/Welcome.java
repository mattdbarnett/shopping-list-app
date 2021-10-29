package com.example.cm6226.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cm6226.MainActivity;
import com.example.cm6226.R;


public class Welcome extends Fragment {

    public Welcome() {
        // Required empty public constructor
    }

    public static Welcome newInstance() {
        Welcome fragment = new Welcome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);

        final MainActivity mainActivity = (MainActivity) getActivity();

        //Welcome back text that includes user's set name ("User" by default)
        AppCompatTextView welcomeText = v.findViewById(R.id.lbl_welcome);
        welcomeText.setText(getResources().getString(R.string.str_welcomescreen) + " " + mainActivity.userName + "!");

        //Sets label to show user total number of items they've added to lists
        AppCompatTextView textAdded = v.findViewById(R.id.lbl_added);
        textAdded.setText(getResources().getString(R.string.str_stats1) + " "
        + mainActivity.getItemsAdded());

        //Sets label to show user total number of unique items they've bought
        AppCompatTextView textBought = v.findViewById(R.id.lbl_bought);
        textBought.setText(getResources().getString(R.string.str_stats2) + " "
        + mainActivity.getItemsBought());

        //Sets label to show user total quantity of items they've bought
        AppCompatTextView textQuantity = v.findViewById(R.id.lbl_quantity);
        textQuantity.setText(getResources().getString(R.string.str_stats3) + " "
        + mainActivity.getTotalQuantity());

        return v;
    }

    //Creates instance of help screen
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_help, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Initialise mainActivity
        final MainActivity mainActivity = (MainActivity) getActivity();

        //Changes fragment to new instance of Help class
        if (id == R.id.action_help){
            mainActivity.changeFragment(
                    new Help(),
                    "listitem");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}