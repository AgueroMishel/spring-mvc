package org.mvc.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.mvc.models.Message;
import org.mvc.models.IncomingMessage;

@Component
public class MessagesHandler {
    private static int nextId = 0;
    private static ConcurrentHashMap<Integer, Message> storage = new ConcurrentHashMap<>();

    public HttpStatus addSingleMessage(IncomingMessage incomingMessage) {
        storage.put(nextId, new Message(nextId, incomingMessage.getUser(), incomingMessage.getMessage()));
        nextId++;

        return HttpStatus.CREATED;
    }

    public List<Message> getAllUnreadMessages() {
        return new ArrayList<>(storage.values());
    }
}
