package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

public class CounterOptions extends AppCompatActivity {

    private Button confirm;
    private EditText ETValue;
    private TextView title, ETLabel, selectorLabel;
    private RadioGroup displaySelector;
    private RadioButton moreDetails, lessDetails;
    private ImageView background;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_options);

        // initialize views
        confirm = findViewById(R.id.counterOptionsConfirmationButton);
        title = findViewById(R.id.counterOptionsTitle);
        ETLabel = findViewById(R.id.ETValueLabel);
        selectorLabel = findViewById(R.id.displaySelectorLabel);
        displaySelector = findViewById(R.id.displaySelectorGroup);
        moreDetails = findViewById(R.id.displaySelectorSwipeList);
        lessDetails = findViewById(R.id.displaySelectorVerticalList);
        background = findViewById(R.id.counterOptionsBackground);
        ETValue = findViewById(R.id.ETValue);
        adView = findViewById(R.id.counterOptionsAdView);

        // set aesthetics
        CounterOptions.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }

                confirm.setTextColor(Color.parseColor(AppConstants.TextColor));
                confirm.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                title.setTextColor(Color.parseColor(AppConstants.TextColor));
                title.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                ETLabel.setTextColor(Color.parseColor(AppConstants.TextColor));
                ETLabel.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                selectorLabel.setTextColor(Color.parseColor(AppConstants.TextColor));
                selectorLabel.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                moreDetails.setTextColor(Color.parseColor(AppConstants.TextColor));
                moreDetails.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                lessDetails.setTextColor(Color.parseColor(AppConstants.TextColor));
                lessDetails.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                ETValue.setTextColor(Color.parseColor(AppConstants.TextColor));
                ETValue.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
            }
        });

        // ETValue can ONLY take numbers greater than zero
        ETValue.setInputType(InputType.TYPE_CLASS_NUMBER);

        // until new views can be added, make the selector group just give toast message
        displaySelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(), "More detailed option is being added, " +
                        "please leave a comment in the app store if interested", Toast.LENGTH_LONG).show();
            }
        });

        // on click listeners
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETValue.getText().toString().equals("")) {
                    startActivity(new Intent(CounterOptions.this, SettingsActivity.class));
                    finish();
                } else {
                    AppConstants.standardDeviation = Integer.parseInt(ETValue.getText().toString());
                    SharedPreferences.Editor editor = AppConstants.prefs.edit();
                    editor.putInt(AppConstants.SDKey, AppConstants.standardDeviation);
                    editor.commit();
                    startActivity(new Intent(CounterOptions.this, SettingsActivity.class));
                    finish();
                }
            }
        });

        // initialize ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // wait for the ad to load
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Connecting to database");
        progress.setMessage("Please wait while loading...");
        progress.setCancelable(false);
        progress.show();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                progress.dismiss();
            }
        }, 6000);
    }
}