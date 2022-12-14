package com.example.passwordio.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.passwordio.DB;
import com.example.passwordio.R;
import com.example.passwordio.models.Folder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class ActionBottomDialogInsertFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    public static final String[] types = {"Login", "Secure Notes"};
    private TextInputEditText folder, type, username, password, uri, noteName, noteText;
    LinearLayout noteLayout, loginLayout;
    private Folder[] folders;
    private DB db;
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
        db = new DB(view.getContext());
        folders = db.allFolders();

        folder = view.findViewById(R.id.bottomSheetInsertFolderText);
        type = view.findViewById(R.id.bottomSheetInsertTypeText);

        username = view.findViewById(R.id.bottomSheetInsertUsername);
        password = view.findViewById(R.id.bottomSheetInsertPassword);
        uri = view.findViewById(R.id.bottomSheetInsertURI);

        noteName = view.findViewById(R.id.bottomSheetInsertNoteName);
        noteText = view.findViewById(R.id.bottomSheetInsertNoteText);

        noteLayout = view.findViewById(R.id.bottomSheetInsertLayoutNote);
        loginLayout = view.findViewById(R.id.bottomSheetInsertLayoutLogin);

        view.findViewById(R.id.bottomSheetInsertCancel).setOnClickListener(this);
        view.findViewById(R.id.bottomSheetInsertSave).setOnClickListener(this);


        loginLayout.setVisibility(View.GONE);
        noteLayout.setVisibility(View.VISIBLE);

        folder.setOnClickListener(this);
        type.setOnClickListener(this);

        type.setText(types[0]);
        changeUIOnType(types[0]);

        folder.setText("No Folder");

    }

    private void handleSave() {
        Log.v("ActionBottomDialogInsertFragment", "handleSave()");
        Log.v("ActionBottomDialogInsertFragment", type.getText().toString());
        String dir = folder.getText().toString();
        long folder_id = -1;
        for (Folder f: folders) {
            if (f.name.equals(dir)){
                folder_id = f.id;
                break;
            }
        }

        if (type.getText().toString().equals("Login")) {
            String uname = username.getText().toString();
            String pswd = password.getText().toString();
            String link = uri.getText().toString();

            Log.v("ActionBottomDialogInsertFragment", uname + " " + password + " " + dir + " " + folder_id);
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "https://" + link;
            }
            db.createLogin(uname, pswd, link, folder_id);
            Toast.makeText(getContext(), "Item Saved", Toast.LENGTH_SHORT);
            dismiss();
            return;
        }
        String noteTitle = noteName.getText().toString();
        String noteContent = noteText.getText().toString();
        db.createNote(noteTitle, noteContent, folder_id);
        Toast.makeText(getContext(), "Item Saved", Toast.LENGTH_SHORT);
        dismiss();
        return;
    }

    private void changeUIOnType(String type) {
        switch (type) {
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
    public void onClick(View view) {
        PopupMenu menu;
        switch (view.getId()) {
            case R.id.bottomSheetInsertCancel:
                dismiss();
                return;
            case R.id.bottomSheetInsertSave:
                handleSave();
                break;
            case R.id.bottomSheetInsertFolderText:
                menu = new PopupMenu(view.getContext(), view);
                menu.getMenu().add("No Folder");
                for (Folder folder: folders) {
                    menu.getMenu().add(folder.name);
                }
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        folder.setText(menuItem.getTitle());
                        return false;
                    }
                });
                menu.show();
                break;
            case R.id.bottomSheetInsertTypeText:
                menu = new PopupMenu(view.getContext(), view);
                for (String type: types) {
                    menu.getMenu().add(type);
                }
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        type.setText(menuItem.getTitle());
                        changeUIOnType(menuItem.getTitle().toString());
                        return false;
                    }
                });
                menu.show();
                break;
        }
    }

}
