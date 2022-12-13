package com.example.passwordio.models;

public class PasswordGeneratorSettingsPreference {
    public int passwordLength;
    public boolean small;
    public boolean capital;
    public boolean number;
    public boolean special;

    public PasswordGeneratorSettingsPreference(int passwordLength, boolean small, boolean capital, boolean number, boolean special) {
        this.passwordLength = passwordLength;
        this.small = small;
        this.capital = capital;
        this.number = number;
        this.special = special;
    }
}
