package ru.itsinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

	@GetMapping("/")
	public String mainPage(ModelMap model) {
		return "index";
	}
}