package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.egovictoria.bestcounterever.R.*;

public class CounterListActivity extends AppCompatActivity {

    private ArrayList<Counter> counters;
    private Button addCounter, deleteCounter, mainMenu, saveSet;
    private ImageView background;
    private ListView counterListView;
    private CounterListAdapter adapter;
    private final String TAG = "BestCounter/CounterList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_counter_list);

        // initialize views
        addCounter = findViewById(id.addCounterButton);
        deleteCounter = findViewById(id.deleteCounterButton);
        mainMenu = findViewById(id.toMainMenuFromCountersList);
        counterListView = findViewById(R.id.CountersListView);
        saveSet = findViewById(id.saveCounterSetButton);
        background = findViewById(id.counterListImageView);

        // get the list of counters
        int[] intentCounters = getIntent().getIntArrayExtra("counters");
        counters = new ArrayList<Counter>();
        if (intentCounters != null) {
            for(int i = 0; i < intentCounters.length; i++) {
                counters.add(new Counter(intentCounters[i]));
            }
        }

        // set adapter and on click listeners
        adapter = new CounterListAdapter(this, layout.counter_list_adapter, counters);
        counterListView.setAdapter(adapter);
        addCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new Counter());
            }
        });
        deleteCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCounters();
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        saveSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "fix it Tori", Toast.LENGTH_SHORT).show();
            }
        });


        // set aesthetics
        CounterListActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                addCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
                deleteCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                deleteCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
                mainMenu.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                mainMenu.setTextColor(Color.parseColor(AppConstants.TextColor));
                saveSet.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                saveSet.setTextColor(Color.parseColor(AppConstants.TextColor));

                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setImageResource(0);
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }
            }
        });
    }
}