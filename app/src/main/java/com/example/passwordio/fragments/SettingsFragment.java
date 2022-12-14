package com.example.passwordio.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.passwordio.DB;
import com.example.passwordio.FolderItemViewActivity;
import com.example.passwordio.FolderListActivity;
import com.example.passwordio.R;
import com.example.passwordio.Settings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements  View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.settingsGenerateDataButton).setOnClickListener(this);
        view.findViewById(R.id.settingsDeleteDataButton).setOnClickListener(this);
        view.findViewById(R.id.fragmentSettingsFolderLayout).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        DB db = new DB(view.getContext());
        switch (view.getId()) {
            case R.id.settingsGenerateDataButton:

                db.generateTestData();
                Toast.makeText(view.getContext(), "Dummy Data Generated", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsDeleteDataButton:
                db.deleteAllData();
                Settings settings = new Settings(getContext());
                settings.setNewUser(true);
                Toast.makeText(view.getContext(), "Data Successfully Deleted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragmentSettingsFolderLayout:
                startActivity(new Intent(this.getContext(), FolderListActivity.class));
                break;
        }
    }
}