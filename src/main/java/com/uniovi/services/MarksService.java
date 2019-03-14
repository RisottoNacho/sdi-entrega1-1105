package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import com.uniovi.repositories.MarksRepository;

@Service
public class MarksService {
	@Autowired
	private MarksRepository marksRepository;

	@Autowired
	private HttpSession httpSession;
/*
	public List<Mark> getMarks() {
		List<Mark> marks = new ArrayList<Mark>();
		marksRepository.findAll().forEach(marks::add);
		return marks;
	}*/

	public Page<Offer> getMarksForUser(Pageable pageable, User user) {
		Page<Offer> marks = new PageImpl<Offer>(new LinkedList<Offer>());
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.findAllByUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = getMarks(pageable);
		}
		return marks;
	}

	/*
	public void setMarkResend(boolean revised, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();
		Mark mark = marksRepository.findById(id).get();
		if (mark.getUser().getDni().equals(dni)) {
			marksRepository.updateResend(revised, id);
		}
	}
*/
	public Page<Offer> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchText, User user) {
		Page<Offer> marks = new PageImpl<Offer>(new LinkedList<Offer>());
		searchText = "%" + searchText + "%";
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = marksRepository.searchByDescriptionAndName(pageable, searchText);
		}
		return marks;
	}

	public Page<Offer> getMarks(Pageable pageable) {
		Page<Offer> marks = marksRepository.findAll(pageable);
		return marks;
	}
	
	public Offer getMark(Long id) {
		Set<Offer> consultedList = (Set<Offer>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Offer>();
		}
		Offer markObtained = marksRepository.findById(id).get();
		consultedList.add(markObtained);
		httpSession.setAttribute("consultedList", consultedList);
		return markObtained;
	}

	public void addMark(Offer mark) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		marksRepository.save(mark);
	}

	public void deleteMark(Long id) {
		marksRepository.deleteById(id);
	}

}
