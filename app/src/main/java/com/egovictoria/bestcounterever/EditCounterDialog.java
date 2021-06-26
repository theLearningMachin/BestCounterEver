package com.egovictoria.bestcounterever;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCounterDialog extends DialogFragment {

    private int position;
    private EditText editCValue, editCName;
    private TextView title, value, name;
    private Button confirmChanges, cancel;

    public EditCounterDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditCounterDialog newInstance(int pos) {
        EditCounterDialog frag = new EditCounterDialog();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_counter_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set position
        position = getArguments().getInt("position");

        // initialize views
        editCName = view.findViewById(R.id.editCounterNameEditText);
        editCValue = view.findViewById(R.id.editCounterValueEditText);
        confirmChanges = view.findViewById(R.id.editCounterDialogConfirmButton);
        cancel = view.findViewById(R.id.exitEditCounterDialogButton);
        title = view.findViewById(R.id.editCounterDialogTitle);
        name = view.findViewById(R.id.editCounterNameTextView);
        value = view.findViewById(R.id.editCounterValueTextView);

        // set aesthetics
        editCName.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        editCName.setTextColor(Color.parseColor(AppConstants.TextColor));
        editCValue.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        editCValue.setTextColor(Color.parseColor(AppConstants.TextColor));
        confirmChanges.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        confirmChanges.setTextColor(Color.parseColor(AppConstants.TextColor));
        cancel.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        cancel.setTextColor(Color.parseColor(AppConstants.TextColor));
        title.setTextColor(Color.parseColor(AppConstants.TextColor));
        name.setTextColor(Color.parseColor(AppConstants.TextColor));
        value.setTextColor(Color.parseColor(AppConstants.TextColor));

        // on click listeners
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Counter counter;
                // if the views are empty, skip this, otherwise update the counter object to return it
                if (!editCName.getText().toString().equals("")) {
                    counter = AppConstants.counters.get(position);
                    counter.setName(editCName.getText().toString());
                    AppConstants.counters.set(position, counter);
                }
                if (!editCValue.getText().toString().equals("")) {
                    counter = AppConstants.counters.get(position);
                    counter.setCount(Integer.parseInt(editCValue.getText().toString()));
                    AppConstants.counters.set(position, counter);
                }
                getDialog().dismiss();
            }
        });
    }
}
