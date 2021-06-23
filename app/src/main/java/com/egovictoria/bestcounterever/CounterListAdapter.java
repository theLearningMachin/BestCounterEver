package com.egovictoria.bestcounterever;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CounterListAdapter extends ArrayAdapter<Counter> {

    private final ArrayList<Counter> counters;
    private final Context context;
    private final int resource;
    private final String TAG = "BestCounter/Adapter";

    public CounterListAdapter(@NonNull Context aContext, int aResource, @NonNull ArrayList<Counter> objects) {
        super(aContext, aResource, objects);
        //Log.i(TAG, "object created");
        counters = objects;
        context = aContext;
        resource = aResource;
        //Log.i(TAG, "variables set");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i(TAG, "Attempting to retrieve view " + position);
        View view;
        Counter counter = counters.get(position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);

            holder.count = view.findViewById(R.id.adapterTextViewCount);
            holder.checkBox = view.findViewById(R.id.adapterCheckBox);
            holder.minus = view.findViewById(R.id.adapterButtonMinus);
            holder.plus =  view.findViewById(R.id.adapterButtonPlus);

            view.setTag(holder);

            //Log.i(TAG, "layout inflater used to create view");
        } else {
            holder = (ViewHolder) convertView.getTag();
            view = convertView;

            //Log.i(TAG, "recycled view used");
        }

        //Log.i(TAG, "attempting to set objects");

        String countText = "" + counter.getCount();
        holder.count.setText(countText);
        // Log.i(TAG, "counter text set");
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "minus button clicked");
                Counter newCounter = counters.get(position);
                newCounter.decrement();
                counters.set(position, counter);
                notifyDataSetChanged();
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "plus button clicked");
                Counter newCounter = counters.get(position);
                newCounter.increment();
                counters.set(position, counter);
                notifyDataSetChanged();
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = holder.checkBox.isChecked();
                Log.i(TAG, "checkBox clicked at position " + position + " set to " + selected);
                Counter newCounter = counters.get(position);
                newCounter.setSelected(selected);
                counters.set(position, newCounter);
            }
        });
        holder.checkBox.setChecked(false);

        // aesthetics
        holder.count.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        holder.count.setTextColor(Color.parseColor(AppConstants.TextColor));
        holder.plus.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        holder.plus.setTextColor(Color.parseColor(AppConstants.TextColor));
        holder.minus.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        holder.minus.setTextColor(Color.parseColor(AppConstants.TextColor));


        return view;
    }

    void deleteCounters() {
        for (int i = (counters.size() - 1); i > -1; i--) {
            if (counters.get(i).isSelected()) {
                Log.i(TAG, "deleting object at index " + i);
                counters.remove(i);
            }
        }
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        CheckBox checkBox;
        TextView count;
        Button plus;
        Button minus;
    }
}
