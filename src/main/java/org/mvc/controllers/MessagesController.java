package org.mvc.controllers;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.mvc.handlers.MessagesHandler;
import org.mvc.models.IncomingMessage;
import org.mvc.models.SignIn;

@RestController
public class MessagesController {
    private MessagesHandler messagesHandler;
    private static final String PAGE_MESSAGES = "messages";
    private static final String MAV_ATTRIBUTE_USER = "user";
    private static final String COOKIE_USER = "user";
    private ModelAndView mav = new ModelAndView();
    private static AtomicBoolean newMessages = new AtomicBoolean(false);

    public MessagesController(MessagesHandler messagesHandler) {
        Objects.requireNonNull(messagesHandler);
        this.messagesHandler = messagesHandler;
    }

    @PostMapping("/signin")
    public ModelAndView signIn(SignIn signIn, HttpServletResponse response) {
        Cookie userCookie = new Cookie(COOKIE_USER, signIn.getUser());
        userCookie.setSecure(true);
        userCookie.setHttpOnly(true);
        userCookie.setPath("/");
        response.addCookie(userCookie);

        Cookie sameSiteCookie = new Cookie("sameSite", "none");
        sameSiteCookie.setSecure(true);
        sameSiteCookie.setHttpOnly(true);
        sameSiteCookie.setPath("/");
        response.addCookie(sameSiteCookie);

        mav.setViewName(PAGE_MESSAGES);
        mav.addObject(MAV_ATTRIBUTE_USER, signIn.getUser());
        mav.addObject(PAGE_MESSAGES, messagesHandler.getAllUnreadMessages());
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/messages")
    public ModelAndView getMessages(
            @CookieValue(COOKIE_USER) String user) {
        generateMessagesView(user);
        mav.setStatus(HttpStatus.OK);

        newMessages.set(false);

        return mav;
    }

    @PostMapping("/messages")
    public ModelAndView addMessage(
            IncomingMessage incomingMessage,
            @CookieValue(COOKIE_USER) String user) {
        mav.setStatus(messagesHandler.addSingleMessage(incomingMessage));
        generateMessagesView(user);

        newMessages.set(true);

        return mav;
    }

    @Async
    @GetMapping("/notify")
    public boolean getNotify() {
        do {
            // Idle until get new messages
        } while (!newMessages.get());

        return true;
    }

    private void generateMessagesView(String user) {
        mav.addObject(MAV_ATTRIBUTE_USER, user);
        mav.addObject(PAGE_MESSAGES, messagesHandler.getAllUnreadMessages());
    }
}