package org.mvc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncomingMessage {
    private String user;
    private String message;

    public IncomingMessage(
            @JsonProperty("user") String user,
            @JsonProperty("message") String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
