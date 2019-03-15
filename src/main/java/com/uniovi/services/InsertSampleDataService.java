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
		User user3 = new User("3@a.com", "Joseph", "Joestar");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("4@a.com", "Bruno", "Buccarati");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("5@a.com", "Dio", "Brando");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("admin@email.com", "Paco", "Mermela");
		user6.setPassword("admin");
		user6.setRole(rolesService.getRoles()[1]);

		@SuppressWarnings("rawtypes")
		Set user1Offers = new HashSet<Offer>() {
			private static final long serialVersionUID = 1L;

			{
				add(new Offer("Vendo Play 2 chipeada",new Date(System.currentTimeMillis()),"Bendo play 2 totalmente jakiada con chip y todos los juegos",20.0,user1));
				add(new Offer("Vendo Opel Corsa",new Date(System.currentTimeMillis()),"Sólo 432543 yardas, opel corsa rojo en perfecto estado",50.0,user1));
				add(new Offer("Bombilla LED",new Date(System.currentTimeMillis()),"LED significa que es poderosa",50.0,user1));
			}
		};
		user1.setOffers(user1Offers);
		Set user2Offers = new HashSet<Offer>() {
			{
				add(new Offer("Termómetro de Mercurio",new Date(System.currentTimeMillis()),"Un termómetro que vino del planeta Mercurio, no confundir, el termómetro es digital.",5.0,user2));
				add(new Offer("Mercurio",new Date(System.currentTimeMillis()),"El elemento químico, no el planeta, no se vayan a confundir",30.0,user2));
				add(new Offer("Plutón",new Date(System.currentTimeMillis()),"Vendo Plutón para quien lo quiera, se lo cambié a un niño por chicles",60.0,user2));
			}
		};
		user2.setOffers(user2Offers);
		Set user3Offers = new HashSet<Offer>() {
			{
				add(new Offer("Cenizas de mi abuelo",new Date(System.currentTimeMillis()),"No me vienen haciendo falta en casa así que pa quien las quiera",5.0,user3));
				add(new Offer("Cocacola",new Date(System.currentTimeMillis()),"Vendo una cocacolita",100.0,user3));
				add(new Offer("Chancla",new Date(System.currentTimeMillis()),"Vendo una chancla porque perdí la otra para alguien que haya perdido un pierna o algo",9.0,user3));
			}
		};
		user3.setOffers(user3Offers);
		Set user4Offers = new HashSet<Offer>() {
			{
				add(new Offer("Chcicles de menta",new Date(System.currentTimeMillis()),"Para tener un aliento frequísimo",1.0,user4));
				add(new Offer("Teclado mecánico",new Date(System.currentTimeMillis()),"Un teclado capaz de arreglar cualquier avería en tu coche",6.0,user4));
				add(new Offer("Acelerador de partículas",new Date(System.currentTimeMillis()),"Ya no acelera como cuando era nuevo así que lo vendo",21.0,user4));
			}
		};
		user4.setOffers(user4Offers);
		Set user5Offers = new HashSet<Offer>() {
			{
				add(new Offer("Mi encarta 2005",new Date(System.currentTimeMillis()),"Key para Mi Primera Encarta 2005, totalmente original, sin activar",5.0,user5));
				add(new Offer("Gato",new Date(System.currentTimeMillis()),"Araña mucho así que he decidido vender al gato, se llama Stroheim",11.0,user5));
				add(new Offer("Polietileno",new Date(System.currentTimeMillis()),"Cuidado, no se debe mezclar nunca con poliuretano",2.0,user5));
			}
		};
		user5.setOffers(user5Offers);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}
}