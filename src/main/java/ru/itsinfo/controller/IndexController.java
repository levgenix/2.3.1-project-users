package ru.itsinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		return "index";
	}
}