package org.mvc.models;

public class Message {
    private int id;
    private String user;
    private String content;

    public Message(int id, String user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
