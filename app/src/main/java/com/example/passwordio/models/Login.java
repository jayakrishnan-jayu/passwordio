package com.example.passwordio.models;

import java.io.Serializable;

public class Login  implements Serializable {
    public String username;
    public String password;
    public String url;

    public Login(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
