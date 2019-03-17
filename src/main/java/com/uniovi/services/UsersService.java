package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.Sdi1105MyWallapopApplication;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//public static final Logger logger = LoggerFactory.getLogger(UsersService.class);

	@PostConstruct
	public void init() {
	}

	public List<User> getUsers(User user) {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		users.remove(users.indexOf(user));
		//logger.info("this is a info message");
		return users;
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public void deleteUser(Long id, User actual) {
		if (id != actual.getId())
			usersRepository.deleteById(id);
	}
}
