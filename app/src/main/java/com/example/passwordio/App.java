package com.example.passwordio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.dialog.ActionBottomDialogInsertFragment;
import com.example.passwordio.fragments.GeneratorFragment;
import com.example.passwordio.fragments.SettingsFragment;
import com.example.passwordio.fragments.VaultFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class App extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener{

    BottomNavigationView bottomNavigationView;
    ImageView addButton, searchButton, historyButton;
    TextView actionBarTitle, actionBarSave;
    View supportActionBar;
    VaultFragment vaultFragment = new VaultFragment();
    GeneratorFragment generatorFragment = new GeneratorFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        actionBarSave = supportActionBar.findViewById(R.id.actionBarSave);

        addButton = supportActionBar.findViewById(R.id.actionBarAdd);
        searchButton = supportActionBar.findViewById(R.id.actionBarSearch);
        historyButton = supportActionBar.findViewById(R.id.actionBarHistory);

        addButton.setOnClickListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.vault);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.vault:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, vaultFragment).commit();
                actionBarTitle.setText("My Vault");
                actionBarSave.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
                historyButton.setVisibility(View.GONE);
                return true;

            case R.id.generator:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, generatorFragment).commit();
                actionBarTitle.setText("Generator");
                actionBarSave.setVisibility(View.GONE);
                addButton.setVisibility(View.GONE);
                searchButton.setVisibility(View.GONE);
                historyButton.setVisibility(View.VISIBLE);
                return true;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                actionBarTitle.setText("Settings");
                actionBarSave.setVisibility(View.GONE);
                addButton.setVisibility(View.GONE);
                searchButton.setVisibility(View.GONE);
                historyButton.setVisibility(View.GONE);
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionBarAdd:
                ActionBottomDialogInsertFragment dialog = ActionBottomDialogInsertFragment.newInstance();
                dialog.show(getSupportFragmentManager(), "test");
        }
    }
}