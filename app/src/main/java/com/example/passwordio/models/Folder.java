package com.example.passwordio.models;

public class Folder {
    public int icon;
    public String name;
    public int count;

    public Folder(String name, int count, int icon) {
        this.icon = icon;
        this.name = name;
        this.count = count;
    }
}
