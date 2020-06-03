package org.mvc.controllers;

import java.util.Objects;

import org.springframework.http.HttpStatus;
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
    private static final String COOKIE_USERNAME = "username";
    private ModelAndView mav = new ModelAndView();

    public MessagesController(MessagesHandler messagesHandler) {
        Objects.requireNonNull(messagesHandler);
        this.messagesHandler = messagesHandler;
    }

    @PostMapping("/signin")
    public ModelAndView signIn(SignIn signIn, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_USERNAME, signIn.getUser());
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        mav.setViewName(PAGE_MESSAGES);
        mav.addObject(MAV_ATTRIBUTE_USER, signIn.getUser());
        mav.addObject(PAGE_MESSAGES, messagesHandler.getAllUnreadMessages());
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/messages")
    public ModelAndView getMessages(
            @CookieValue(COOKIE_USERNAME) String username) {
        generateMessagesView(username);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @PostMapping("/messages")
    public ModelAndView addMessage(
            IncomingMessage incomingMessage,
            @CookieValue(COOKIE_USERNAME) String username) {
        mav.setStatus(messagesHandler.addSingleMessage(incomingMessage));
        generateMessagesView(username);

        return mav;
    }

    private void generateMessagesView(String username) {
        mav.addObject(MAV_ATTRIBUTE_USER, username);
        mav.addObject(PAGE_MESSAGES, messagesHandler.getAllUnreadMessages());
    }
}