package com.example.passwordio.models;

import java.io.Serializable;

public class Note implements Serializable {
    public long id;
    public String name;
    public String note;
    public long folder_id;

    public Note(long id, String name, String note, long folder_id) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.folder_id = folder_id;
    }

    public Note(int id, String name) {
        this(id, name, "", -1);
    }

    public Note(String name) {
        this(-1, name);
    }
}
