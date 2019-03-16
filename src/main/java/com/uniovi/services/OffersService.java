package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class OffersService {
	@Autowired
	private OffersRepository offersRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private HttpSession httpSession;
	/*
	 * public List<Mark> getMarks() { List<Mark> marks = new ArrayList<Mark>();
	 * marksRepository.findAll().forEach(marks::add); return marks; }
	 */

	// METHOD NEEDS TO BE FIXED
	public Page<Offer> getOffersForUser(Pageable pageable, User user) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.findAllByUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_ADMIN")) {
			offers = getOffers(pageable);
		}
		return offers;
	}

	public List<Offer> getOffersForUserNoPageable(User user) {
		List<Offer> offers = new LinkedList<Offer>();
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.findAllByUserNoPageable(user);
		}
		return offers;
	}

	public Page<Offer> getOffersBuyedByUser(Pageable pageable, User user) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.findAllBuyedByUser(pageable, user.getEmail());
		}
		return offers;
	}

	public List<Offer> getOffersBuyedByUserNoPageable(User user) {
		List<Offer> offers = new LinkedList<Offer>();
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.findAllBuyedByUserNoPageable(user.getEmail());
		}
		return offers;
	}

	public Page<Offer> getAllOffersExceptUser(Pageable pageable, User user) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.findAllExceptUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_ADMIN")) {
			offers = offersRepository.findAll(pageable);
		}
		return offers;
	}

	public void setOfferBuyed(boolean buyed, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		offersRepository.updateBuyed(buyed, id, email);
	}

	public boolean buyOffer(User user, Long id) {
		Offer offer = offersRepository.findById(id).get();
		if (user.getMoney() >= offer.getPrice()) {
			user.setMoney(user.getMoney() - offer.getPrice());
			setOfferBuyed(true, id);
			User seller = offer.getUser();
			seller.setMoney(seller.getMoney() + offer.getPrice());
			user.getOffers().add(offer);
			usersRepository.save(user);
			usersRepository.save(seller);
			return true;
		}
		return false;
	}

	/*
	 * public Page<Offer> searchOffersByDescriptionAndNameForUser(Pageable pageable,
	 * String searchText, User user) { Page<Offer> offers = new PageImpl<Offer>(new
	 * LinkedList<Offer>()); searchText = "%" + searchText + "%"; if
	 * (user.getRole().equals("ROLE_STUDENT")) { offers =
	 * offersRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
	 * } if (user.getRole().equals("ROLE_PROFESSOR")) { offers =
	 * offersRepository.searchByDescriptionAndName(pageable, searchText); } return
	 * offers; }
	 */

	public Page<Offer> searchOffersByDescriptionAndNameExceptUser(Pageable pageable, String searchText, User user) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		searchText = "%" + searchText + "%";
		if (user.getRole().equals("ROLE_USER")) {
			offers = offersRepository.searchByTitleDescriptionNameExceptUser(pageable, searchText, user);
		}
		return offers;
	}

	public Page<Offer> getOffers(Pageable pageable) {
		Page<Offer> offers = offersRepository.findAll(pageable);
		return offers;
	}

	public Offer getOffer(Long id) {
		@SuppressWarnings("unchecked")
		Set<Offer> consultedList = (Set<Offer>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Offer>();
		}
		Offer offerObtained = offersRepository.findById(id).get();
		consultedList.add(offerObtained);
		httpSession.setAttribute("consultedList", consultedList);
		return offerObtained;
	}

	public void addOffer(Offer offer) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		offersRepository.save(offer);
	}

	public void deleteOffer(Long id, User user) {
		for (Offer o : user.getOffers()) {
			if (o.getId() == id)
				offersRepository.deleteById(id);
		}
	}

}
