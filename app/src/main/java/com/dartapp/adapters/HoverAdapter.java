package com.dartapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dartapp.R;

import java.util.List;

public class HoverAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;

    public HoverAdapter(Context context, List<String> objects) {
        super(context, R.layout.spinner_dropdown_item, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position == 0) {
            // Set the hint text color
            ((TextView) view.findViewById(android.R.id.text1)).setTextColor(Color.GRAY);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        // Set a hover effect here if needed

        return view;
    }
}

