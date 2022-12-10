package com.example.passwordio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;

enum PasswordioTheme {
    LIGHT,
    DARK
}

public class Settings {
    private static Context context;
    private static final String PrefName = "passwordioPref";
    private static final String prefTheme = "theme";
    private static final String prefNewUser = "newUser";

    private static SharedPreferences sharedPreference;
    private static SharedPreferences.Editor editor;

    public Settings(Context context) {
        sharedPreference = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public PasswordioTheme getTheme() {
        String theme = sharedPreference.getString(prefTheme, PasswordioTheme.LIGHT.toString());
        return PasswordioTheme.valueOf(theme);
    }

    public void setTheme(PasswordioTheme theme) {
        editor.putString(prefTheme, theme.name());
        editor.commit();
    }

    public boolean isNewUser() {
        return sharedPreference.getBoolean(prefNewUser, true);
    }

    public void setNewUser(boolean b) {
        editor.putBoolean(prefNewUser, b);
        editor.commit();
    }

    public static void setCustomActionBar(ActionBar actionBar) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0F0F0F")));
    }
}