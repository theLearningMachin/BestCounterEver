package com.egovictoria.bestcounterever;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCounterDialog extends DialogFragment {

    private int position;
    private EditText editCValue, editCName;
    private Button confirmChanges, cancel;

    public EditCounterDialog(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_counter_dialog, container, false);

        // initialize views
        editCName = view.findViewById(R.id.editCounterNameEditText);
        editCValue = view.findViewById(R.id.editCounterValueEditText);
        confirmChanges = view.findViewById(R.id.editCounterDialogConfirmButton);
        cancel = view.findViewById(R.id.exitEditCounterDialogButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the views are empty, skip this, otherwise update the counter object to return it
                if (!editCName.getText().toString().equals("")) {
                    AppConstants.counters.get(position).setName(editCName.getText().toString());
                }
                if (!editCValue.getText().toString().equals("")) {
                    AppConstants.counters.get(position).setCount(Integer.parseInt(editCValue.getText().toString()));
                }
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
