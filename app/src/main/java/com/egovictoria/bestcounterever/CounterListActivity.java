package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.egovictoria.bestcounterever.R.*;

public class CounterListActivity extends AppCompatActivity {

    private ArrayList<Counter> counters;
    private Button addCounter, deleteCounter;
    private ListView counterListView;
    private CounterListAdapter adapter;
    private final String TAG = "BestCounter/CounterList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_counter_list);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // initialize views
        addCounter = findViewById(id.addCounterButton);
        deleteCounter = findViewById(id.deleteCounterButton);
        counterListView = findViewById(id.CountersListView);

        int[] intentCounters = getIntent().getIntArrayExtra("counters");
        if (intentCounters != null) {
            counters = new ArrayList<Counter>();
            for(int i = 0; i < intentCounters.length; i++) {
                counters.add(new Counter(intentCounters[i]));
            }
        } else {
            counters = new ArrayList<Counter>();
        }

        adapter = new CounterListAdapter(this, layout.counter_list_adapter, counters);
        counterListView.setAdapter(adapter);

        addCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "attempting to add a new counter");
                adapter.add(new Counter());
            }
        });
        deleteCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCounters();
            }
        });
    }
}