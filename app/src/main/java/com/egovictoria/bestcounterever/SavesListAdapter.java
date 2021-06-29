package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
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

    private static final String TAG = "BestCounter/SLAdapter";
    private Context context;
    private ArrayList<String> saves;
    private int resource;

    public SavesListAdapter(@NonNull Context con, int aResource, @NonNull ArrayList<String> objects) {
        super(con, aResource, objects);
        context = con;
        saves = objects;
        resource = aResource;

        String logText = "{";
        for (String save : saves) {
            logText += save + ",";
        }
        logText += "}";

        Log.i(TAG, "adapter created, list: " + logText);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(TAG, "getting view " + position);

        View view;
        ViewHolder holder;
        String saveName = saves.get(position);

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
        Log.i(TAG, "retrieving counters for save " + saveName);

        ArrayList<Object> items = SaveReaderWriter.getSet(saveName);
        ArrayList<Counter> counters = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            counters.add((Counter) items.get(i));
        }
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
