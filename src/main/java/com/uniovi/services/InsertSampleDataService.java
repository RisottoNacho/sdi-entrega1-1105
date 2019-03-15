package com.uniovi.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {
		User user1 = new User("1@a.com", "Barack", "Obama");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("2@a.com", "Paco", "Sanz");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		User user6 = new User("admin@email.com", "Paco", "Mermela");
		user6.setPassword("admin");
		user6.setRole(rolesService.getRoles()[1]);

		Set user1Offers = new HashSet<Offer>() {
			{
				add(new Offer("Vendo Play 2 chipeada",new Date(System.currentTimeMillis()),"Bendo play 2 totalmente jakiada con chip y todos los juegos",20.0,user1));
				add(new Offer("Vendo Opel Corsa",new Date(System.currentTimeMillis()),"Sólo 432543 yardas, opel corsa rojo en perfecto estado",50.0,user1));
			}
		};
		user1.setMarks(user1Offers);
		Set user2Offers = new HashSet<Offer>() {
			{
				add(new Offer("Cenizas de mi abuelo",new Date(System.currentTimeMillis()),"No me vienen haciendo falta en casa así que pa quien las quiera",5.0,user2));
				add(new Offer("Plutón",new Date(System.currentTimeMillis()),"Vendo plutón para quien lo quiera, se lo cambié a un niño por chicles",60.0,user2));
			}
		};
		user2.setMarks(user2Offers);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user6);
	}
}