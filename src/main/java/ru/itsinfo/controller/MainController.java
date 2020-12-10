package ru.itsinfo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itsinfo.model.User;

public class MainController {

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") { //TODO читерство
            return new User();
        }
        return (User) authentication.getPrincipal();
    }

}
