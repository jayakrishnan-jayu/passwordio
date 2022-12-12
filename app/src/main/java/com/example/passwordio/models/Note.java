package com.example.passwordio.models;

import java.io.Serializable;

public class Note implements Serializable {
    public long id;
    public String name;
    public String note;

    public Note(long id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    public Note(int id, String name) {
        this(id, name, "");
    }

    public Note(String name) {
        this(-1, name);
    }
}
