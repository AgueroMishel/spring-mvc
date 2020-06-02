package org.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MessagesHandler messagesHandler;
    private static final String MESSAGES_PAGE = "messages";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USERNAME_COOKIE = "username";
    private ModelAndView mav = new ModelAndView();

    @PostMapping("/signin")
    public ModelAndView signIn(SignIn signIn, HttpServletResponse response) {
        Cookie cookie = new Cookie(USERNAME_COOKIE, signIn.getUser());
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        mav.setViewName(MESSAGES_PAGE);
        mav.addObject(USER_ATTRIBUTE, signIn.getUser());
        mav.addObject(MESSAGES_PAGE, messagesHandler.getAllUnreadMessages());
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/messages")
    public ModelAndView getMessages(
            @CookieValue("username") String username) {
        generateMessagesView(username);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @PostMapping("/messages")
    public ModelAndView addMessage(
            IncomingMessage incomingMessage,
            @CookieValue("username") String username) {
        mav.setStatus(messagesHandler.addSingleMessage(incomingMessage));
        generateMessagesView(username);

        return mav;
    }

    private void generateMessagesView(String username) {
        mav.addObject(USER_ATTRIBUTE, username);
        mav.addObject(MESSAGES_PAGE, messagesHandler.getAllUnreadMessages());
    }
}