package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class LoadCounterSetActivity extends AppCompatActivity {

    private Button confirm, menu, delete;
    private ListView savesList;
    private ImageView background;
    private static final String TAG = "BestCounter/load";
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_counter_set);

        // initialize views
        confirm = findViewById(R.id.confirmSetSelectionButton);
        savesList = findViewById(R.id.saveListView);
        savesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        background = findViewById(R.id.saveCounterSetBackground);
        menu = findViewById(R.id.toMainMenuFromLoadCounterActivity);
        delete = findViewById(R.id.deleteCounterSetButton);

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
                delete.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                delete.setTextColor(Color.parseColor(AppConstants.TextColor));
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
                // fetch the string from save file and convert to list of counters with save load helper
                String tag = AppConstants.saveNames.get(position);
                AppConstants.counters = SaveLoadHelper.fromString(AppConstants.counterSRW.getItem(tag));
                selectedPosition = position;
                Toast.makeText(getApplicationContext(), tag + " retrieved", Toast.LENGTH_SHORT).show();
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = AppConstants.saveNames.get(selectedPosition);
                AppConstants.counterSRW.delete(tag);
                AppConstants.saveNames = AppConstants.counterSRW.getTags();
                adapter.notifyDataSetChanged();
            }
        });
        Log.i(TAG, "on click listeners set");
    }
}