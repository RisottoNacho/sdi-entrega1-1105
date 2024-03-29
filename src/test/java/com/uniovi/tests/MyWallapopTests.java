package com.uniovi.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;
import com.uniovi.tests.pageobjects.PO_AddOfferView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyWallapopTests {

	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private UsersRepository usersRepository;

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox64 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver022 = "C:\\Users\\Dio Brando\\Desktop\\gecko\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox64, Geckdriver022);
	static String URL = "http://localhost:8090";
	static String URLremota = "http://urlsdispring:xxxx";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@SuppressWarnings("all")
	public void initdb() {
		// Borramos todas las entidades.
		usersRepository.deleteAll();
		// Ahora las volvemos a crear
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

		Set user1Offers = new HashSet<Offer>() {
			private static final long serialVersionUID = 1L;

			{
				add(new Offer("Vendo Play 2 chipeada", new Date(System.currentTimeMillis()),
						"Bendo play 2 totalmente jakiada con chip y todos los juegos", 20.0, user1));
				add(new Offer("Vendo Opel Corsa", new Date(System.currentTimeMillis()),
						"Sólo 432543 yardas, opel corsa rojo en perfecto estado", 50.0, user1));
				add(new Offer("Bombilla LED", new Date(System.currentTimeMillis()), "LED significa que es poderosa",
						50.0, user1));
				add(new Offer("Pelucas", new Date(System.currentTimeMillis()), "Hechas con animal muerto", 20.0,
						user1));
				add(new Offer("Mazapanes", new Date(System.currentTimeMillis()), "Caseron, los hizo la mama", 50.0,
						user1));
				add(new Offer("Acciones de ", new Date(System.currentTimeMillis()), "LED significa que es poderosa",
						50.0, user1));
			}
		};
		user1.setOffers(user1Offers);
		Set user2Offers = new HashSet<Offer>() {
			{
				add(new Offer("Termómetro de Mercurio", new Date(System.currentTimeMillis()),
						"Un termómetro que vino del planeta Mercurio, no confundir, el termómetro es digital.", 5.0,
						user2));
				add(new Offer("Mercurio", new Date(System.currentTimeMillis()),
						"El elemento químico, no el planeta, no se vayan a confundir", 30.0, user2));
				add(new Offer("Plutón", new Date(System.currentTimeMillis()),
						"Vendo Plutón para quien lo quiera, se lo cambié a un niño por chicles", 60.0, user2));
			}
		};
		user2.setOffers(user2Offers);
		Set user3Offers = new HashSet<Offer>() {
			{
				add(new Offer("Cenizas de mi abuelo", new Date(System.currentTimeMillis()),
						"No me vienen haciendo falta en casa así que pa quien las quiera", 5.0, user3));
				add(new Offer("Cocacola", new Date(System.currentTimeMillis()), "Vendo una cocacolita", 100.0, user3));
				add(new Offer("Chancla", new Date(System.currentTimeMillis()),
						"Vendo una chancla porque perdí la otra para alguien que haya perdido un pierna o algo", 9.0,
						user3));
			}
		};
		user3.setOffers(user3Offers);
		Set user4Offers = new HashSet<Offer>() {
			{
				add(new Offer("Chcicles de menta", new Date(System.currentTimeMillis()),
						"Para tener un aliento frequísimo", 1.0, user4));
				add(new Offer("Teclado mecánico", new Date(System.currentTimeMillis()),
						"Un teclado capaz de arreglar cualquier avería en tu coche", 6.0, user4));
				add(new Offer("Acelerador de partículas", new Date(System.currentTimeMillis()),
						"Ya no acelera como cuando era nuevo así que lo vendo", 21.0, user4));
			}
		};
		user4.setOffers(user4Offers);
		Set user5Offers = new HashSet<Offer>() {
			{
				add(new Offer("Mi encarta 2005", new Date(System.currentTimeMillis()),
						"Key para Mi Primera Encarta 2005, totalmente original, sin activar", 5.0, user5));
				add(new Offer("Gato", new Date(System.currentTimeMillis()),
						"Araña mucho así que he decidido vender al gato, se llama Stroheim", 11.0, user5));
				add(new Offer("Polietileno", new Date(System.currentTimeMillis()),
						"Cuidado, no se debe mezclar nunca con poliuretano", 2.0, user5));
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

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		initdb();
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "pe@gmail.com", "Josefo", "Perez", "77777", "77777");
		PO_View.checkElement(driver, "text", "Estas son sus ofertas a la venta");
	}

	// Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos
	// vacíos).
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, " ", "Josefo", "Perez", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Este campo no puede estar vacío");
		PO_RegisterView.fillForm(driver, "pe@gmail.com", " ", "Perez", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Este campo no puede estar vacío");
		PO_RegisterView.fillForm(driver, "pe@gmail.com", "Josefo", " ", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Este campo no puede estar vacío");
	}

	// Registro de Usuario con datos inválidos (repetición de contraseña inválida).
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "pe@gmail.com", "Josefo", "Perez", "77777", "55555");
		PO_RegisterView.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	// Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "1@a.com", "Josefo", "Perez", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Este Email ya existe");
	}

	// Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_HomeView.checkElement(driver, "text", "admin@email.com");
	}

	// Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_HomeView.checkElement(driver, "text", "1@a.com");
	}

	// Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos)
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, " ", "123456");
		PO_HomeView.checkElement(driver, "text", "Email o contraseña incorrectos.");
		PO_LoginView.fillForm(driver, "1@a.com", " ");
		PO_HomeView.checkElement(driver, "text", "Email o contraseña incorrectos.");
		PO_LoginView.fillForm(driver, " ", " ");
		PO_HomeView.checkElement(driver, "text", "Email o contraseña incorrectos.");
	}

	// Inicio de sesión con datos válidos (usuario estándar, email existente, pero
	// contraseña
	// incorrecta).
	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "12356");
		PO_HomeView.checkElement(driver, "text", "Email o contraseña incorrectos.");
	}

	// Inicio de sesión con datos inválidos (usuario estándar, email no existente en
	// la aplicación).
	@Test
	public void PR09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "145@a.com", "123456");
		PO_HomeView.checkElement(driver, "text", "Email o contraseña incorrectos.");
	}

	// Hacer click en la opción de salir de sesión y comprobar que se redirige a la
	// página de inicio
	// de sesión (Login).
	@Test
	public void PR10() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_HomeView.checkElement(driver, "text", "Identifícate");
	}

	// Comprobar que el botón cerrar sesión no está visible si el usuario no está
	// autenticado.
	@Test
	public void PR11() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

	// Mostrar el listado de usuarios y comprobar que se muestran todos los que
	// existen en el sistema.
	@Test
	public void PR12() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver, "1@a.com");
		SeleniumUtils.textoPresentePagina(driver, "2@a.com");
		SeleniumUtils.textoPresentePagina(driver, "3@a.com");
		SeleniumUtils.textoPresentePagina(driver, "4@a.com");
		SeleniumUtils.textoPresentePagina(driver, "5@a.com");
	}

//	Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
//	y dicho usuario desaparece
	@Test
	public void PR13() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver, "1@a.com");
		driver.findElement(By.id("cb-1@a.com")).click();
		driver.findElement(By.name("delete")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "1@a.com");
	}

//	Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
	// y dicho usuario desaparece
	@Test
	public void PR14() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver, "5@a.com");
		driver.findElement(By.id("cb-5@a.com")).click();
		driver.findElement(By.name("delete")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "5@a.com");
	}

//	Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
//	usuarios desaparecen.
	@Test
	public void PR15() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver, "5@a.com");
		SeleniumUtils.textoPresentePagina(driver, "4@a.com");
		SeleniumUtils.textoPresentePagina(driver, "3@a.com");
		driver.findElement(By.id("cb-5@a.com")).click();
		driver.findElement(By.id("cb-4@a.com")).click();
		driver.findElement(By.id("cb-3@a.com")).click();
		driver.findElement(By.name("delete")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "5@a.com");
		SeleniumUtils.textoNoPresentePagina(driver, "4@a.com");
		SeleniumUtils.textoNoPresentePagina(driver, "3@a.com");
	}

//	Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el botón Submit.
//	Comprobar que la oferta sale en el listado de ofertas de dicho usuario.
	@Test
	public void PR16() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offerAdd");
		PO_AddOfferView.fillForm(driver, "Selenium test", "This is a test for the webPage", "6.0");
		PO_HomeView.checkElement(driver, "text", "Selenium test");
	}

//	Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo título vacío) y pulsar
//	el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio
	@Test
	public void PR17() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offerAdd");
		PO_AddOfferView.fillForm(driver, " ", "This is a test for the webPage", "6.0");
		PO_LoginView.checkElement(driver, "text", "Este campo no puede estar vacío");
		PO_AddOfferView.fillForm(driver, "Selenium test", " ", "6.0");
		PO_LoginView.checkElement(driver, "text", "Este campo no puede estar vacío");
		PO_AddOfferView.fillForm(driver, "Selenium test", "This is a test for the webPage", " ");
		PO_LoginView.checkElement(driver, "text", "Este campo no puede estar vacío");
	}

//	Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que
	// existen para este usuario.
	@Test
	public void PR18() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_HomeView.checkElement(driver, "text", "Cenizas de mi abuelo");
		PO_HomeView.checkElement(driver, "text", "Cocacola");
		PO_HomeView.checkElement(driver, "text", "Chancla");
	}

//	Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y
//	que la oferta desaparece.
	@Test
	public void PR19() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Cocacola");
		driver.findElement(By.id("del-Cocacola")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Cocacola");
	}

//	Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que la lista se actualiza y
	// que la oferta desaparece
	@Test
	public void PR20() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Chancla");
		driver.findElement(By.id("del-Chancla")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Chancla");
	}

//	Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
//	corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR21() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		driver.findElement(By.className("btn")).click();
		List<WebElement> elementos;
		for (int i = 0; i < 2; i++) {
			elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			assertTrue(elementos.size() == 5);
			driver.findElement(By.id("nextPage")).click();
		}
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

	}

//	Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
//	muestra la página que corresponde, con la lista de ofertas vacía.
	@Test
	public void PR22() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		WebElement txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Esta cosa no está entre mis preciosas ofertas");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Mazapanes");
		SeleniumUtils.textoNoPresentePagina(driver, "Pelucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Acciones de Bankia");
		SeleniumUtils.textoNoPresentePagina(driver, "Mercurio");
		SeleniumUtils.textoNoPresentePagina(driver, "Cocacola");
	}

//	Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja
//	un saldo positivo en el contador del comprobador. Y comprobar que el contador se actualiza
// en la vista del comprador.
	@Test
	public void PR23() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "3@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		WebElement txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Mazapanes");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoPresentePagina(driver, "Mazapanes");
		driver.findElement(By.id("buy-Mazapanes-1@a.com")).click();
		assertTrue(driver.findElement(By.id("moneyNav")).getText().compareTo("50.0") == 0);

	}

//	Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja
//	un saldo 0 en el contador del comprobador. Y comprobar que el contador se actualiza correctamente en
//	la vista del comprador. .
	@Test
	public void PR24() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		WebElement txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Cocacola");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoPresentePagina(driver, "Cocacola");
		driver.findElement(By.id("buy-Cocacola-3@a.com")).click();
		assertTrue(driver.findElement(By.id("moneyNav")).getText().compareTo("0.0") == 0);

	}

//	Sobre una búsqueda determinada (a elección de desarrollador), intentar comprar una oferta
//	que esté por encima de saldo disponible del comprador. Y comprobar que se muestra el mensaje de
	// saldo no suficiente
	@Test
	public void PR25() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		WebElement txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Cocacola");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoPresentePagina(driver, "Cocacola");
		driver.findElement(By.id("buy-Cocacola-3@a.com")).click();
		assertTrue(driver.findElement(By.id("moneyNav")).getText().compareTo("0.0") == 0);
		txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Chancla");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoPresentePagina(driver, "Chancla");
		SeleniumUtils.textoPresentePagina(driver, "Saldo insuficiente");
		try {
			driver.findElement(By.id("buy-Chancla-3@a.com")).click();
			assertFalse(true);
		} catch (NoSuchElementException e) {
			assertFalse(false);
		}
		
	}

//	Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen
//	las ofertas que deben aparecer.
	@Test
	public void PR26() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offersView");
		WebElement txtSearch = driver.findElement(By.name("searchText"));
		txtSearch.click();
		txtSearch.clear();
		txtSearch.sendKeys("Cocacola");
		driver.findElement(By.className("btn")).click();
		SeleniumUtils.textoPresentePagina(driver, "Cocacola");
		driver.findElement(By.id("buy-Cocacola-3@a.com")).click();
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "@id", "homeProfile",
				PO_View.getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
		// Ahora lo clickamos
		elementos.get(0).click();
		PO_HomeView.checkElement(driver, "text", "Estas son sus ofertas a la venta");
		PO_HomeView.checkElement(driver, "text", "Cocacola");
	}

	
//	Visualizar al menos cuatro páginas en Español/Inglés/Español (comprobando que algunas
//			de las etiquetas cambian al idioma correspondiente). Página principal/Opciones Principales de
//			Usuario/Listado de Usuarios de Admin/Vista de alta de Oferta
	@Test
	public void PR27() {
		PO_NavView.changeIdiom(driver, "btnSpanish");
		SeleniumUtils.textoPresentePagina(driver, "Regístrese ahora para empezar a comprar");
		PO_NavView.changeIdiom(driver, "btnEnglish");
		SeleniumUtils.textoPresentePagina(driver, "Sing in now and start buying");
		PO_NavView.changeIdiom(driver, "btnSpanish");
		SeleniumUtils.textoPresentePagina(driver, "Regístrese ahora para empezar a comprar");

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Usuario autentificado como");
		PO_NavView.changeIdiom(driver, "btnEnglish");
		SeleniumUtils.textoPresentePagina(driver, "These are your purchased offers");
		PO_NavView.changeIdiom(driver, "btnSpanish");
		SeleniumUtils.textoPresentePagina(driver, "Estas son sus ofertas compradas");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_HomeView.checkElement(driver, "text", "admin@email.com");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver,
				"Los usuarios que actualmente figuran en el sistema son los siguientes");
		PO_NavView.changeIdiom(driver, "btnEnglish");
		SeleniumUtils.textoPresentePagina(driver,
				"The users currently registered in the system are the ones listed bellow");
		PO_NavView.changeIdiom(driver, "btnSpanish");
		SeleniumUtils.textoPresentePagina(driver,
				"Los usuarios que actualmente figuran en el sistema son los siguientes");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Usuario autentificado como");
		PO_NavView.clickDropdownMenuOption(driver, "offers-dropdown", "offers-menu", "offerAdd");
		SeleniumUtils.textoPresentePagina(driver, "Ofertas");
		PO_NavView.changeIdiom(driver, "btnEnglish");
		SeleniumUtils.textoPresentePagina(driver, "Offers");
		PO_NavView.changeIdiom(driver, "btnSpanish");
		SeleniumUtils.textoPresentePagina(driver, "Ofertas");
	}
	
//	Intentar acceder sin estar autenticado a la opción de listado de usuarios del administrador. Se
//	deberá volver al formulario de login
	@Test
	public void PR28() {
		initdb();
		driver.get(URL + "/user/list");
		PO_View.checkElement(driver, "text", "Iniciar sesión");
	}
	
//	Intentar acceder sin estar autenticado a la opción de listado de ofertas propias de un usuario
//	estándar. Se deberá volver al formulario de login.
	@Test
	public void PR29() {
		initdb();
		driver.get(URL + "/offer/list");
		PO_View.checkElement(driver, "text", "Iniciar sesión");
		driver.get(URL + "/home");
		PO_View.checkElement(driver, "text", "Iniciar sesión");
	}
	
//	Estando autenticado como usuario estándar intentar acceder a la opción de listado de
//	usuarios del administrador. Se deberá indicar un mensaje de acción prohibida.
	@Test
	public void PR30() {
		initdb();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@a.com", "123456");
		driver.get(URL + "/user/list");
		SeleniumUtils.textoPresentePagina(driver, "HTTP Status 403 – Forbidden");
	}


	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}
}