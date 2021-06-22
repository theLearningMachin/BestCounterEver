package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import static com.egovictoria.bestcounterever.R.*;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button selectBackgroundOption, selectFancyBorder, counterOptions, counterListOptions, mainMenu;
    private final String TAG = "BestCounter/SettingMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_settings);

        selectBackgroundOption = findViewById(id.selectBackgroundOptionButton);
        selectFancyBorder = findViewById(id.selectFancyBorderButton);
        counterOptions = findViewById(id.CounterOptionsButton);
        counterListOptions = findViewById(id.counterListOptionsButton);
        mainMenu = findViewById(id.toMainMenuFromSettingsMenuButton);

        selectBackgroundOption.setOnClickListener(this);
        selectFancyBorder.setOnClickListener(this);
        counterOptions.setOnClickListener(this);
        counterListOptions.setOnClickListener(this);
        mainMenu.setOnClickListener(this);

        Log.i(TAG, "initialized");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.selectBackgroundOptionButton) {
            startActivity(new Intent(this, Backgrounds.class));
        } else if (id == R.id.selectFancyBorderButton) {
            Toast.makeText(this, "This is being added soon!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.CounterOptionsButton) {
            Toast.makeText(this, "Add counter options activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.counterListOptionsButton) {
            Toast.makeText(this, "Add counter list options activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toMainMenuFromSettingsMenuButton) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}