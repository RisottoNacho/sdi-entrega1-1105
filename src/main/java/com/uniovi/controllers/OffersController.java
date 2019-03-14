package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;

@Controller
public class OffersController {

	@Autowired // Inyectar el servicio
	private OffersService marksService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/offer/list/update")
	public String updateList(Model model,Pageable pageable, Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> offers = marksService.getMarksForUser(pageable, user);
		model.addAttribute("markList", offers.getContent() );
		return "mark/list :: tableMarks";
	}

	@RequestMapping("/offer/list")
	public String getList(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> marks = new PageImpl<Offer>(new LinkedList<Offer>());
		if (searchText != null && !searchText.isEmpty()) {
			marks = marksService.searchMarksByDescriptionAndNameForUser(pageable, searchText, user);
		} else {
			marks = marksService.getMarksForUser(pageable, user);
		}
		model.addAttribute("page", marks);
		model.addAttribute("markList", marks.getContent());
		return "offer/list";
	}

	@RequestMapping(value = "/offer/{id}/resend", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		marksService.setMarkResend(true, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/{id}/noresend", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		marksService.setMarkResend(false, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer mark) {
		marksService.addMark(mark);
		return "redirect:/offer/list";
	}

	@RequestMapping("/offer/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "offer/details";
	}

	@RequestMapping("/offer/delete/{id}")
	public String deleteOffer(@PathVariable Long id) {
		marksService.deleteMark(id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/add")
	public String getOffer(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "offer/add";
	}

	@RequestMapping(value = "/offer/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "offer/edit";
	}

	@RequestMapping(value = "/offer/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Offer mark) {
		Offer original = marksService.getMark(id);
		// modificar solo score y description
		original.setScore(mark.getScore());
		original.setDescription(mark.getDescription());
		marksService.addMark(original);
		return "redirect:/offer/details/" + id;
	}

}
