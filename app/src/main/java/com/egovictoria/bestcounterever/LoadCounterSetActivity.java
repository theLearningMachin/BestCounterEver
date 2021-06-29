package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadCounterSetActivity extends AppCompatActivity {

    private Button confirm, menu;
    private ListView savesList;
    private ImageView background;
    private TextView selection;
    private static final String TAG = "BestCounter/load";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_counter_set);

        // initialize views
        confirm = findViewById(R.id.confirmSetSelectionButton);
        savesList = findViewById(R.id.saveListView);
        background = findViewById(R.id.saveCounterSetBackground);
        selection = findViewById(R.id.saveSelectionName);
        menu = findViewById(R.id.toMainMenuFromLoadCounterActivity);

        Log.i(TAG, "views created");

        // aesthetics
        LoadCounterSetActivity.this.runOnUiThread(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }

                confirm.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                confirm.setTextColor(Color.parseColor(AppConstants.TextColor));
                selection.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                selection.setTextColor(Color.parseColor(AppConstants.TextColor));
                menu.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                menu.setTextColor(Color.parseColor(AppConstants.TextColor));

                Log.i(TAG, "aesthetics set");
            }
        });

        Log.i(TAG, "creating adapter with saves list: " + AppConstants.saveNames.toString());


        // set on click listeners and adapter
        SavesListAdapter adapter = new SavesListAdapter(
                getApplicationContext(),
                R.layout.saves_list_adapter,
                AppConstants.saveNames
        );
        Log.i(TAG, "adapter created");

        savesList.setAdapter(adapter);
        Log.i(TAG, "adapter set to list: " + AppConstants.saveNames.toString());



        savesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Object> items = SaveReaderWriter.getSet(AppConstants.saveNames.get(position));
                AppConstants.counters = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    AppConstants.counters.add((Counter) items.get(i));
                }
                selection.setText(AppConstants.saveNames.get(position));
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the counters list activity after setting counters in app constants
                startActivity(new Intent(getApplicationContext(), CounterListActivity.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        Log.i(TAG, "on click listeners set");
    }
}