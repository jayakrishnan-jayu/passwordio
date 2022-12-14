package com.example.passwordio.models;

import java.io.Serializable;

public class Login  implements Serializable {
    public final long id;
    public String username;
    public String password;
    public String url;
    public long folder_id;

    public Login(long id, String username, String password, String url, long folder_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.url = url;
        this.folder_id = folder_id;
    }
}
