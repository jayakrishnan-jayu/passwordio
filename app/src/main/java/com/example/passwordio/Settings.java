package com.example.passwordio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import androidx.appcompat.app.ActionBar;

import com.example.passwordio.models.PasswordGeneratorSettingsPreference;

enum PasswordioTheme {
    LIGHT,
    DARK
}

public class Settings {
    private static Context context;
    private static final String PrefName = "passwordioPref";
    private static final String prefTheme = "theme";
    private static final String prefNewUser = "newUser";

    private static final String prefGenPswdLength = "genPswdLength";
    private static final String prefGenSmall = "genSmall";
    private static final String prefGenCapital = "genCapital";
    private static final String prefGenNumbers = "genNum";
    private static final String prefGenSpecial = "genSpecial";

    private static final String prefUserName = "genUserName";
    private static final String prefUserPasswd = "genUserPasswd";

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
    
    public PasswordGeneratorSettingsPreference getGeneratorSettingsPreference() {
        return new PasswordGeneratorSettingsPreference(
                sharedPreference.getInt(prefGenPswdLength, 8),
                sharedPreference.getBoolean(prefGenSmall, true),
                sharedPreference.getBoolean(prefGenCapital, false),
                sharedPreference.getBoolean(prefGenNumbers, false),
                sharedPreference.getBoolean(prefGenSpecial, false)
        );
    }

    public void setGeneratorSettingsPrefernece(PasswordGeneratorSettingsPreference settings) {
        editor.putInt(prefGenPswdLength, settings.passwordLength);
        editor.putBoolean(prefGenSmall, settings.small);
        editor.putBoolean(prefGenCapital, settings.capital);
        editor.putBoolean(prefGenNumbers, settings.number);
        editor.putBoolean(prefGenSpecial, settings.special);
        editor.commit();
    }
    
    public boolean isNewUser() {
        return sharedPreference.getBoolean(prefNewUser, true);
    }

    public void setNewUser(boolean b) {
        editor.putBoolean(prefNewUser, b);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreference.getString(prefUserName, "");
    }
    public void createNewUser(String email, String password) {
        Log.v("Settings", "Creating account with "+email+" "+password);
        editor.putString(prefUserName, email);
        editor.putString(prefUserPasswd, password);
        editor.commit();
        setNewUser(false);
    }

    public boolean authenticated(String password) {
        Log.v("Settings", "authenticated account with "+password+" "+sharedPreference.getString(prefUserPasswd, ""));
        return sharedPreference.getString(prefUserPasswd, "").equals(password);
    }

    public static void setCustomActionBar(ActionBar actionBar) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0F0F0F")));
    }
}