package com.unimas.e_nelayanadmin.Model;

public class User {
    private Boolean isAdmin;

    public User() {
    }

    public User(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
