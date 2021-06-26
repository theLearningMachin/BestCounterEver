package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LoadCounterSetActivity extends AppCompatActivity {

    private Button confirm, menu;
    private ListView savesList;
    private ImageView background;
    private TextView selection;

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
            }
        });

        // set on click listeners and adapter
        SavesListAdapter adapter = new SavesListAdapter(
                getApplicationContext(),
                R.layout.saves_list_adapter,
                AppConstants.srw.getSaveNames()
        );
        savesList.setAdapter(adapter);
        savesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstants.counters = AppConstants.srw.getSet(AppConstants.srw.getSaveNames()[position]);
                selection.setText(AppConstants.srw.getSaveNames()[position]);
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
    }
}