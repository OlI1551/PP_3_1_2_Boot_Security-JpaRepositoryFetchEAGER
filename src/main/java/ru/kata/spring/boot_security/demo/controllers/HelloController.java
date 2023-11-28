package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HelloController {

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm a Spring Boot Starter");
		messages.add("3.1.5 version by 19th of oct'23");
		messages.add("And I can represent you");
		messages.add("a Spring Boot Security application");
		messages.add("with optional user roles!");
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping("/user")
	public String showUserPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "user";
	}
}