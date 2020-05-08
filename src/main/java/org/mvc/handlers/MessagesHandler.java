package org.mvc.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.mvc.models.IncomingMessage;
import org.springframework.http.HttpStatus;

import org.mvc.models.Message;

public class MessagesHandler {
    private static MessagesHandler instance;
    private static int nextId = 0;
    private static ConcurrentHashMap<Integer, Message> storage;

    private MessagesHandler() {}

    public static MessagesHandler getInstance() {
        if(instance == null) {
            instance = new MessagesHandler();
            storage = new ConcurrentHashMap<>();
        }

        return instance;
    }

    public HttpStatus addSingleMessage(IncomingMessage incomingMessage) {
        storage.put(nextId, new Message(nextId, incomingMessage.getUser(), incomingMessage.getMessage()));
        nextId++;

        return HttpStatus.CREATED;
    }

    public List<Message> getAllUnreadMessages() {
        return new ArrayList<>(storage.values());
    }
}
