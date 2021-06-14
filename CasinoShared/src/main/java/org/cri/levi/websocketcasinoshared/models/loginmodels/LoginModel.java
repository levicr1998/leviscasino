package org.cri.levi.websocketcasinoshared.models.loginmodels;

public class LoginModel {

    public String getUsername() {
        return username;
    }

    private String username;

    public String getPassword() {
        return password;
    }

    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginModel() {

    }
}
