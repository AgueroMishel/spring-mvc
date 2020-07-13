package org.mvc.models;

public class IncomingMessage {
    private String user;
    private String message;

    public IncomingMessage(String user, String message) {
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
