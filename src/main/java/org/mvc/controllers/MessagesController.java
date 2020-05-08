package org.mvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.mvc.handlers.MessagesHandler;
import org.mvc.models.IncomingMessage;
import org.mvc.models.SignIn;

@RestController
public class MessagesController {
    ModelAndView mav = new ModelAndView();

    @PostMapping("/signin")
    public ModelAndView signIn(SignIn signIn) {
        mav.clear();
        mav.setViewName("messages");
        mav.addObject("user", signIn.getUser());
        mav.addObject("messages", MessagesHandler.getInstance().getAllUnreadMessages());
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/messages")
    public ModelAndView getMessages() {
        mav.addObject("messages", MessagesHandler.getInstance().getAllUnreadMessages());
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @PostMapping("/messages")
    public ModelAndView addMessage(IncomingMessage incomingMessage) {
        mav.setStatus(MessagesHandler.getInstance().addSingleMessage(incomingMessage));
        mav.addObject("messages", MessagesHandler.getInstance().getAllUnreadMessages());

        return mav;
    }
}