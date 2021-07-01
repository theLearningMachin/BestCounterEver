package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import static com.egovictoria.bestcounterever.R.*;

public class CounterListActivity extends AppCompatActivity {

    private Button addCounter, deleteCounter, mainMenu, saveSet;
    private ImageView background;
    private ListView counterListView;
    private CounterListAdapter adapter;
    private static final String TAG = "BestCounter/CounterList";
    private static FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_counter_list);

        // get the fragment manager
        fm = getSupportFragmentManager();
        Log.i(TAG, "fragment manager got");

        // initialize views
        addCounter = findViewById(id.addCounterButton);
        deleteCounter = findViewById(id.deleteCounterButton);
        mainMenu = findViewById(id.toMainMenuFromCountersList);
        counterListView = findViewById(R.id.CountersListView);
        saveSet = findViewById(id.saveCounterSetButton);
        background = findViewById(id.counterListImageView);

        Log.i(TAG, "views initialized");

        try {
            // set adapter and on click listeners
            adapter = new CounterListAdapter(this, layout.counter_list_adapter, AppConstants.counters);
            Log.i(TAG, "adapter created");
        } catch (Exception e) {
            String name = e.getClass().getCanonicalName();
            Log.i(TAG, "error " + name + " at creating adapter");
        }

        try {
            counterListView.setAdapter(adapter);
        } catch (Exception e) {
            String name = e.getClass().getCanonicalName();
            Log.i(TAG, "Exception type " + name + " while attempting to set adapter");
        }

        Log.i(TAG, "adapter set");

        addCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new Counter());
                Log.i(TAG, "new counter successfully added");
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
                Log.i(TAG, "");
                // initialize shit
                AlertDialog.Builder builder = new AlertDialog.Builder(CounterListActivity.this);
                AlertDialog dialog = null;
                View view = getLayoutInflater().inflate(layout.save_counter_dialog, null);
                EditText saveNameEntry = view.findViewById(id.saveCounterDialogEntry);
                Button confirmEntry = view.findViewById(id.saveCounterDialogConfirmButton);

                // if there is already a save name for the counter set, put it in saveNameEntry
                if (AppConstants.CurrentSaveName != null) {
                    saveNameEntry.setText(AppConstants.CurrentSaveName);
                }
                confirmEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = saveNameEntry.getText().toString();
                        String details = SaveLoadHelper.stringList(AppConstants.counters);

                        AppConstants.counterSRW.addItem(name, details);
                        Toast.makeText(getApplicationContext(),
                                "Saved successfully, tap outside the dialog to dismiss",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view);
                dialog = builder.create();
                dialog.show();
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

                if (AppConstants.getTheme().equals("bright") ||
                        AppConstants.getTheme().equals("dark")) {
                    background.setImageResource(0);
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }
            }
        });
    }

    static void counterOptionsPopup(int position) {
        try{
            EditCounterDialog dialog = EditCounterDialog.newInstance(position);
            Log.i(TAG, "dialog initialized");
            dialog.show(fm, "Edit Counter");
        } catch (Exception e) {
            String name = e.getClass().getCanonicalName();
            Log.i(TAG, "error " + name + " during dialog popup");
            Log.i(TAG, e.toString());
        }
    }
}