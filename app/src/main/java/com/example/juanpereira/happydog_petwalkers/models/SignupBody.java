package com.example.juanpereira.happydog_petwalkers.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignupBody {

    @JsonProperty("nombre")
    private String name;

    private String email;

    private String password;

    @JsonProperty("esPaseador")
    private boolean isDogWalker;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDogWalker() {
        return isDogWalker;
    }

    public void setDogWalker(boolean dogWalker) {
        isDogWalker = dogWalker;
    }
}
