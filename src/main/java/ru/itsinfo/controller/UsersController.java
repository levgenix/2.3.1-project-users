package ru.itsinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itsinfo.model.User;
import ru.itsinfo.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping({"", "/", "list"}) //TODO
	public String userList(Model model,
						   @ModelAttribute("flashMessage") String flashAttribute) {

		model.addAttribute("users", userService.readAllUsers());
//		setFlashMessage("dsfsdfdsfsdf"); //TODO
		return "users/list";
	}

	@GetMapping("/create")
	public String addUserForm(@ModelAttribute("user") User user) {
		return "users/form";
	}

	@GetMapping(value = "/edit", params = "id")
	public String edidtUserForm(@RequestParam("id") int id,
								RedirectAttributes attributes, Model model) {
		try {
			model.addAttribute("user", userService.readUser(id));
		} catch (NumberFormatException | NullPointerException e) {
			attributes.addFlashAttribute("flashMessage", "User are not exists!");
			return "redirect:/users/list";
		}
		return "users/form";
	}

	@PostMapping()
	public RedirectView saveUser(@ModelAttribute("user") User user,
								 RedirectAttributes attributes) {
		if (null == user.getId()) {
			userService.createUser(user);
		} else {
			userService.updateUser(user);
		}

		attributes.addFlashAttribute("flashMessage", "User " + user.getFirstName() + " successfully created!");
		return new RedirectView("/users/list");
	}

	@GetMapping("/delete")
	public RedirectView deleteUser(@RequestParam(value = "id", required = true, defaultValue = "") String id, // TODO не проверять
								   RedirectAttributes attributes) {
		try {
			User user = userService.deleteUser(Integer.parseUnsignedInt(id));
			attributes.addFlashAttribute("flashMessage", "User " + user.getFirstName() + " successfully deleted!");
		} catch (NumberFormatException | NullPointerException e) {
			attributes.addFlashAttribute("flashMessage", "User are not exists!");
		}

		return new RedirectView("/users/list");
	}

	@ModelAttribute("flashMessage")
	public String setFlashMessage(String message) {
		return message;
	}
}