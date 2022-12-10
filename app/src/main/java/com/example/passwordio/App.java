package com.example.passwordio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.passwordio.fragments.GeneratorFragment;
import com.example.passwordio.fragments.SettingsFragment;
import com.example.passwordio.fragments.VaultFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class App extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    VaultFragment vaultFragment = new VaultFragment();
    GeneratorFragment generatorFragment = new GeneratorFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.vault);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.v(this.getClass().getName().toString(), "item "+item.getItemId()+" "+(item.getItemId()==R.id.vault));
        switch (item.getItemId()) {

            case R.id.vault:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, vaultFragment).commit();
                return true;

            case R.id.generator:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, generatorFragment).commit();
                return true;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                return true;
        }
        return false;
    }
}