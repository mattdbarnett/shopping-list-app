package com.example.cm6226;

/*
    -- Shopping List Program --

    @author Matt Barnett
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.cm6226.Fragments.ItemAdd;
import com.example.cm6226.Fragments.ItemList;
import com.example.cm6226.Fragments.Settings;
import com.example.cm6226.Fragments.Welcome;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    //DB Initialisation
    DatabaseHelper databaseHelper;

    //Nav Initialisation
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    //History + List Initialisation
    public static Integer itemsAdded = 0;
    public static Integer itemsBought = 0;
    public static Integer totalQuantity = 0;
    public static String userName = "User";
    public ArrayList<Item> listOfItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set default username
        userName = getResources().getString(R.string.str_user);

        //Start databasehelper
        databaseHelper = new DatabaseHelper(this);

        // Toolbar and Nav //
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping List Application");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_closed
        ) {
            //Closes keyboard when nav used
            //Reference - https://stackoverflow.com/questions/18430186/how-do-i-close-the-keyboard-when-the-navigation-drawer-opens/21056618
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
            //Reference end
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_panel);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().performIdentifierAction(R.id.menu_welcome, 0); //Set default fragment
        // Toolbar and Nav //
    }

    //When an item is selected in the navigation drawer:
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_welcome) { //Home Page Selected
            toolbar.setTitle(getResources().getString(R.string.str_home));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Welcome())
                    .commit();
        }
        if (id == R.id.menu_nav_a) { //Shopping List Selected
            toolbar.setTitle(getResources().getString(R.string.str_shoppinglist));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ItemList())
                    .commit();
        }
        else if (id == R.id.menu_nav_b) { //Add Item Page Selected
            toolbar.setTitle(getResources().getString(R.string.str_additem));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ItemAdd())
                    .commit();
        }
        else if (id == R.id.menu_settings) { //Settings Selected
            toolbar.setTitle(getResources().getString(R.string.str_settings));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Settings())
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    //Used to create fragments for each item in the shopping list.
    public void changeFragment(Fragment fragment, String backstacktag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(backstacktag)
                .commit();
    }

    //Adds item to ArrayList, updates history
    public void itemAdd(String name, String quantity) {
        Item newItem = new Item(name, quantity, false);
        itemsAdded += 1;
        listOfItems.add(newItem);
        Toast.makeText(this, getResources().getString(R.string.str_toast_itemadded) + " (" + name + ")", Toast.LENGTH_SHORT).show();
    }


    //Changes the project resources between welsh and english
     public void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            configuration.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(configuration, displayMetrics);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    //Returns current language for radio buttons
    public String getLanguage() {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        return configuration.locale.toString();
    }

    //Get methods to retrieve history variables
    public Integer getItemsAdded() {
        return itemsAdded;
    }

    public Integer getItemsBought() {
        return itemsBought;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    //Save each item in ArrayList to database and current user info
    public void Save() {
        for(Item item : listOfItems) {
            databaseHelper.addItem(item.getName(), item.getQuantity());
        }
        databaseHelper.addUser(userName, getItemsAdded().toString(), getItemsBought().toString(), getTotalQuantity().toString());
        Toast.makeText(this, getResources().getString(R.string.str_toast_savesuccess), Toast.LENGTH_LONG).show();
    }

    //Load last saved state of application
    public void Load() {
        try { //Try load save

            //Initialise db data
            Cursor user = databaseHelper.getUser();
            Cursor data = databaseHelper.getItems();
            user.moveToFirst();

            //Set history variables
            userName = user.getString(0);
            itemsAdded = parseInt(user.getString(1));
            itemsBought = parseInt(user.getString(2));
            totalQuantity = parseInt(user.getString(3));

            //Clear ArrayList then add each saved item
            listOfItems.clear();
            while(data.moveToNext()) {
                Item currentItem = new Item(data.getString(0), data.getString(1), false);
                listOfItems.add(currentItem);
            }
            Toast.makeText(this, getResources().getString(R.string.str_toast_loadsuccess), Toast.LENGTH_LONG).show();
        }
        catch (Exception e) { //If no save present, catch error and notify user
            Toast.makeText(this, getResources().getString(R.string.str_toast_loadfail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}