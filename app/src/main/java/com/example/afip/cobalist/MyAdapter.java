package com.example.afip.cobalist;

/**
 * Created by afip on 5/17/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> animalList = new ArrayList<>();

    public MyAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        animalList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_row, null);
        TextView textView1 = (TextView) v.findViewById(R.id.title);
        TextView textView2 = (TextView) v.findViewById(R.id.artist);
        TextView textView3 = (TextView) v.findViewById(R.id.duration);
        ImageView imageView = (ImageView) v.findViewById(R.id.list_image);
        textView1.setText(animalList.get(position).getAnimalName());
        textView2.setText(animalList.get(position).getKategori());
        textView3.setText(animalList.get(position).getDate());
        imageView.setImageResource(animalList.get(position).getAnimalImage());
        return v;

    }

}