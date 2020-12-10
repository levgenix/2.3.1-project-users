package ru.itsinfo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController extends MainController {

    @GetMapping()
    public String showUserInfo(Model model) {
        //TODO взять из currentUser
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            model.addAttribute("principal", authentication.getPrincipal());
            return "/user-info";
        }
        return "redirect:/accessdenied";
    }
}
