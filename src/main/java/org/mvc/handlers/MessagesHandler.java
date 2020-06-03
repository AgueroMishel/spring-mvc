package org.mvc.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.mvc.models.Message;
import org.mvc.models.IncomingMessage;

@Component
public class MessagesHandler {
    private static AtomicInteger nextId = new AtomicInteger(0);
    private static ConcurrentHashMap<Integer, Message> storage = new ConcurrentHashMap<>();

    public HttpStatus addSingleMessage(IncomingMessage incomingMessage) {
        storage.put(nextId.get(), new Message(nextId.get(), incomingMessage.getUser(), incomingMessage.getMessage()));
        nextId.addAndGet(1);

        return HttpStatus.CREATED;
    }

    public List<Message> getAllUnreadMessages() {
        return new ArrayList<>(storage.values());
    }
}
