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

    private EditText editCValue, editCName;
    private Button confirmChanges;

    public EditCounterDialog(){}

    public static EditCounterDialog newInstance(String title) {
        EditCounterDialog dialog = new EditCounterDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_counter_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        editCName = view.findViewById(R.id.editCounterNameEditText);
        editCValue = view.findViewById(R.id.editCounterValueEditText);
        confirmChanges = view.findViewById(R.id.editCounterDialogConfirmButton);
    }
}
