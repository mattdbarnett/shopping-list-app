package com.example.cm6226;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> data;
    private int layoutToUseForTheRows;

    public ListAdapter(Context context, int layout, ArrayList<Item> data) {
        this.context = context;
        this.data = data;
        this.layoutToUseForTheRows = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) { //could return a specific type rather than object, e.g. Book
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(this.layoutToUseForTheRows, parent, false);
        }

        final Item item = (Item) this.getItem(position);

        //Retrieve button
        Button button = (Button) convertView.findViewById(R.id.list_item_btn);

        //When "Buy" button is pressed, remove item from data-set and update global variables.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, context.getResources().getString(R.string.str_toast_itemremoved) + " (" + item.getName() + ")", Toast.LENGTH_SHORT).show();
                data.remove(item);
                notifyDataSetChanged();
                MainActivity.itemsBought = MainActivity.itemsBought + 1;
                MainActivity.totalQuantity = MainActivity.totalQuantity + parseInt(item.getQuantity());
            }
        });

        //Set name field for each item
        AppCompatTextView topText = convertView.findViewById(R.id.row_top);
        topText.setText(item.getName());

        //Set quantity field for each item
        AppCompatTextView bottomText = convertView.findViewById(R.id.row_bottom);
        bottomText.setText(item.getQuantity());

        return convertView;
    }
}
