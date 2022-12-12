package com.example.passwordio.models;

import java.io.Serializable;

public class Login  implements Serializable {
    public final long id;
    public final String username;
    public final String password;
    public final String url;

    public Login(long id, String username, String password, String url) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
