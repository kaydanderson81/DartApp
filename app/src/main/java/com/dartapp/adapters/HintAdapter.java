package com.dartapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class HintAdapter extends ArrayAdapter<String> {

    public HintAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
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
        View view = super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            // Hide the hint in the dropdown
            view.setVisibility(View.GONE);
        }
        return view;
    }
}
