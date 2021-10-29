package com.example.cm6226.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cm6226.Item;
import com.example.cm6226.ListAdapter;
import com.example.cm6226.MainActivity;
import com.example.cm6226.MyItemRecyclerViewAdapter;
import com.example.cm6226.R;
import com.example.cm6226.dummy.DummyContent;

import java.util.ArrayList;

public class ItemList extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private ListView listView;

    private ArrayList<Item> listOfItems = new ArrayList<Item>();

    public ItemList() {
    }

    @SuppressWarnings("unused")
    public static ItemList newInstance(int columnCount) {
        ItemList fragment = new ItemList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itemlist, container, false);

        listView = (ListView) view.findViewById(R.id.list);

        final MainActivity mainActivity = (MainActivity) getActivity();

        //Sync listview with global list
        listOfItems = mainActivity.listOfItems;

        final ListAdapter listAdapter = new ListAdapter(
                getActivity(),
                R.layout.fragment_itemlist_individual,
                listOfItems
        );

        listView.setAdapter(listAdapter);

        //When an item is clicked, create an new instance ItemFragment for that item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item currentItem = (Item) listView.getItemAtPosition(position);
                mainActivity.changeFragment(
                        ItemFragment.newInstance(
                                String.valueOf(currentItem.getName()),
                                String.valueOf(currentItem.getQuantity()),
                                String.valueOf(currentItem)
                        ),
                        "listitem"
                );
            }
        });

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }

    public ListView getListView() {
        return listView;
    }
}