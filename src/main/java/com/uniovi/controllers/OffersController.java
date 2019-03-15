package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddOfferValidator;

@Controller
public class OffersController {

	@Autowired // Inyectar el servicio
	private OffersService offersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddOfferValidator addOfferValidator;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/offer/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> offers = offersService.getOffersForUser(pageable, user);
		model.addAttribute("offerList", offers.getContent());
		return "offer/list :: tableOffers";
	}

	@RequestMapping("/offer/list")
	public String getList(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Offer> offer = new PageImpl<Offer>(new LinkedList<Offer>());
		if (searchText != null && !searchText.isEmpty()) {
			offer = offersService.searchOffersByDescriptionAndNameExceptUser(pageable, searchText, user);
		} else {
			offer = offersService.getAllOffersExceptUser(pageable, user);
		}
		model.addAttribute("page", offer);
		model.addAttribute("offerList", offer.getContent());
		return "offer/list";
	}

	@RequestMapping(value = "/offer/buy/{id}", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		offersService.buyOffer(activeUser, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/{id}/nobuy", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		offersService.setOfferBuyed(false, id);
		return "redirect:/offer/list";
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.GET)
	public String getOffer(Model model) {
		model.addAttribute("offer", new Offer());
		return "/offer/add";
	}
/*
	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer offer) {
		offer.setDate(new Date(System.currentTimeMillis()));
		offersService.addOffer(offer);
		return "redirect:/offer/list";
	}*/

	@RequestMapping("/offer/delete/{id}")
	public String deleteOffer(@PathVariable Long id) {
		offersService.deleteOffer(id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String getOffer(@Validated Offer offer, BindingResult result, Model model) {
		addOfferValidator.validate(offer, result);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		if (result.hasErrors()) {
			return "/offer/add";
		}
		offer.setDate(new Date(System.currentTimeMillis()));
		offer.setUser(activeUser);
		offersService.addOffer(offer);
		return "redirect:/home";
	}

	/*
	 * // PENDIENTE DE IMPLEMENTACIÓN
	 * 
	 * @RequestMapping(value = "/offer/edit/{id}", method = RequestMethod.POST)
	 * public String setEdit(Model model, @PathVariable Long id, @ModelAttribute
	 * Offer offer) { Offer original = offersService.getOffer(id); // modificar solo
	 * score y description original.setScore(offer.getScore());
	 * original.setDescription(offer.getDescription());
	 * //offersService.getOffer(original); return "redirect:/offer/details/" + id; }
	 */

}
