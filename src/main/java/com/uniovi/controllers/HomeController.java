package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Controller
public class HomeController {
	@Autowired
	private UsersService usersService;

	@RequestMapping("/")
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().toString().equals("anonymousUser")) {
			String email = auth.getName();
			User activeUser = usersService.getUserByEmail(email);
			model.addAttribute("money", activeUser.getMoney());
		}
		return "index";
	}
}