package com.egovictoria.bestcounterever;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SavesListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] saves;
    private int resource;

    public SavesListAdapter(@NonNull Context con, int aResource, @NonNull String[] objects) {
        super(con, aResource, objects);
        context = con;
        saves = objects;
        resource = aResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder holder;
        String saveName = saves[position];

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);

            holder.saveName = (TextView) view.findViewById(R.id.saveItemNameTextView);
            holder.saveScroll = (TextView) view.findViewById(R.id.saveItemScrollingText);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        holder.saveName.setText(saveName);
        holder.saveScroll.setText(getCountersText(saveName));
        holder.saveScroll.setSelected(true);

        // aesthetics
        holder.saveName.setTextColor(Color.parseColor(AppConstants.TextColor));
        holder.saveScroll.setTextColor(Color.parseColor(AppConstants.TextColor));

        return view;
    }

    private String getCountersText(String saveName) {
        ArrayList<Counter> counters = AppConstants.srw.getSet(saveName);
        String output = "";
        for(int i = 0; i < counters.size(); i++) {
            output += (counters.get(i).getName() + ":" + counters.get(i).getCount() + "   ");
        }
        return output;
    }

    private static class ViewHolder {
        TextView saveName, saveScroll;
    }
}
