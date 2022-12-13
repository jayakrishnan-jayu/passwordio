package com.example.passwordio.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.passwordio.DB;
import com.example.passwordio.R;
import com.example.passwordio.models.Folder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ActionBottomDialogInsertFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    public static final String[] types = {"Login", "Secure Notes"};

    public static ActionBottomDialogInsertFragment newInstance() {
        return new ActionBottomDialogInsertFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_insert_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);;
        DB db = new DB(view.getContext());
        Folder[] folders = db.allFolders();
        String[] result = new String[folders.length+1];
        result[0] = "No Folder";
        for (int i=0; i< folders.length; i++) {
            result[i+1] = folders[i].name;
        }

        LinearLayout noteLayout = view.findViewById(R.id.bottomSheetInsertLayoutNote);
        LinearLayout loginLayout = view.findViewById(R.id.bottomSheetInsertLayoutLogin);

        view.findViewById(R.id.bottomSheetInsertCancel).setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, types);
        Spinner spinner = view.findViewById(R.id.bottomSheetInsertTypeSpinner);
        spinner.setAdapter(adapter);
        loginLayout.setVisibility(View.GONE);
        noteLayout.setVisibility(View.VISIBLE);

        Spinner folderSpinner = view.findViewById(R.id.bottomSheetInsertFolderSpinner);
        ArrayAdapter folderAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, result);
        folderSpinner.setAdapter(folderAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (types[i]) {
                    case "Login":
                        noteLayout.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                        break;
                    case "Secure Notes":
                        loginLayout.setVisibility(View.GONE);
                        noteLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottomSheetInsertCancel:
                dismiss();
                return;
        }
    }
}
