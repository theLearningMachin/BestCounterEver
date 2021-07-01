package com.egovictoria.bestcounterever;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SavesListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "BestCounter/SLAdapter";
    private Context context;
    private ArrayList<String> saves;
    private int resource;
    private int selectedPosition;
    private RadioButton selectedRB;

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
            holder.button = (RadioButton) view.findViewById(R.id.pickSelectSetRadioButton);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(position != selectedPosition && selectedRB != null){
                    selectedRB.setChecked(false);
                }

                selectedPosition = position;
                selectedRB = (RadioButton)v;
            }
        });


        if(selectedPosition != position){
            holder.button.setChecked(false);
        }else{
            holder.button.setChecked(true);
            if(selectedRB != null && holder.button != selectedRB){
                selectedRB = holder.button;
            }
        }

        holder.saveName.setText(saveName);
        holder.saveScroll.setText(getCountersText(saveName));
        holder.saveScroll.setSelected(true);
        holder.button.setChecked(false);

        // aesthetics
        holder.saveName.setTextColor(Color.parseColor(AppConstants.TextColor));
        holder.saveScroll.setTextColor(Color.parseColor(AppConstants.TextColor));

        return view;
    }

    private String getCountersText(String saveName) {
        ArrayList<Counter> counters = SaveLoadHelper.fromString(AppConstants.counterSRW.getItem(saveName));

        String output = "";
        for(int i = 0; i < counters.size(); i++) {
            output += (counters.get(i).getName() + ":" + counters.get(i).getCount() + "   ");
        }
        return output;
    }

    private static class ViewHolder {
        TextView saveName, saveScroll;
        RadioButton button;
    }
}
