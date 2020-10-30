package ru.itsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itsinfo.model.User;

@Controller
public class IndexController {

	/*
        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username" /></p>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

        </sec:authorize>
	 */

	@GetMapping()
	public String mainPage(Model model) {
		// TODO при ?logout выводить прощальное сообщение
//		System.out.println("Index controller");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			System.out.println(authentication.getPrincipal());
			System.out.println(authentication.getAuthorities());
		}
		// TODO как отловить неудачный логин "/?error" @PostMapping
		return "index";
	}

	@PostMapping("/j_spring_security_check")
	public void getLogin(@RequestParam(required = false, defaultValue = "" ) String j_username,
						 @RequestParam(required = false, defaultValue = "" ) String pass,
						 Model model) {
System.exit(1);
		System.out.println("lOGIN: " + j_username);
	}

	@GetMapping("logout")
	public RedirectView logout(RedirectAttributes attributes) {
		attributes.addAttribute("logout");
		return new RedirectView("/");
	}
}