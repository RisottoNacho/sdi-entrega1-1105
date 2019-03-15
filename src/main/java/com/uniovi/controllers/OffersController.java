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
	private OffersService offersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/offer/list/update")
	public String updateList(Model model,Pageable pageable, Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> offers = offersService.getOffersForUser(pageable, user);
		model.addAttribute("offerList", offers.getContent() );
		return "offer/list :: tableOffers";
	}

	@RequestMapping("/offer/list")
	public String getList(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> offer = new PageImpl<Offer>(new LinkedList<Offer>());
		if (searchText != null && !searchText.isEmpty()) {
			offer = offersService.searchOffersByDescriptionAndNameForUser(pageable, searchText, user);
		} else {
			offer = offersService.getOffersForUser(pageable, user);
		}
		model.addAttribute("page", offer);
		model.addAttribute("offerList", offer.getContent());
		return "offer/list";
	}

	@RequestMapping(value = "/offer/{id}/buy", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		offersService.setOfferBuyed(true, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/{id}/nobuy", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		offersService.setOfferBuyed(false, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer offer) {
		offersService.addOffer(offer);
		return "redirect:/offer/list";
	}

	// remove later
	@RequestMapping("/offer/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("mark", offersService.getOffer(id));
		return "offer/details";
	}

	@RequestMapping("/offer/delete/{id}")
	public String deleteOffer(@PathVariable Long id) {
		offersService.deleteOffer(id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/add")
	public String getOffer(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "offer/add";
	}

	// remove later
	@RequestMapping(value = "/offer/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", offersService.getOffer(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "offer/edit";
	}

	// PENDIENTE DE IMPLEMENTACIÓN
	@RequestMapping(value = "/offer/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Offer offer) {
		Offer original = offersService.getOffer(id);
		// modificar solo score y description
		original.setScore(offer.getScore());
		original.setDescription(offer.getDescription());
		//offersService.getOffer(original);
		return "redirect:/offer/details/" + id;
	}

}
