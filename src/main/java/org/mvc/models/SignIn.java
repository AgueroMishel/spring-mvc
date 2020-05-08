package org.mvc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignIn {
    private String user;

    public SignIn(
            @JsonProperty("user") String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
