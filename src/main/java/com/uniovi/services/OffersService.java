package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

@Service
public class OffersService {
	@Autowired
	private OffersRepository offersRepository;

	@Autowired
	private HttpSession httpSession;
/*
	public List<Mark> getMarks() {
		List<Mark> marks = new ArrayList<Mark>();
		marksRepository.findAll().forEach(marks::add);
		return marks;
	}*/

	
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

	
	public void setOfferBuyed(boolean buyed, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Offer offer = offersRepository.findById(id).get();
		if (offer.getUser().getEmail().equals(email)) {
			offersRepository.updateBuyed(buyed, id);
		}
	}

	public Page<Offer> searchOffersByDescriptionAndNameForUser(Pageable pageable, String searchText, User user) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		searchText = "%" + searchText + "%";
		if (user.getRole().equals("ROLE_STUDENT")) {
			offers = offersRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			offers = offersRepository.searchByDescriptionAndName(pageable, searchText);
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

	public void deleteOffer(Long id) {
		offersRepository.deleteById(id);
	}

}
