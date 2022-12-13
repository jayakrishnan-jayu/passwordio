package com.example.passwordio.models;

import java.io.Serializable;

public class Folder implements Serializable {
    public long id;
    public String name;
    public long count;

    public Folder(long id, String name, long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public Folder(String name) {
        this(0, name, 0);
    }
}
