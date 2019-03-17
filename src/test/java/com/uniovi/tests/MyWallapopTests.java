package com.uniovi.tests;

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
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
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
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickDropdownMenuOption(driver, "users-dropdown", "users-menu", "viewUsers");
		SeleniumUtils.textoPresentePagina(driver, "1@a.com");
		driver.findElement(By.id("cb-1@a.com")).click();
		driver.findElement(By.name("delete")).click();
		SeleniumUtils.textoNoPresentePagina(driver, "1@a.com");
	}

//	Visualizar al menos cuatro páginas en Español/Inglés/Español (comprobando que algunas
//			de las etiquetas cambian al idioma correspondiente). Página principal/Opciones Principales de
//			Usuario/Listado de Usuarios de Admin/Vista de alta de Oferta
	@Test
	public void PR27() {
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

	public void PR06d() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
		// COmprobamos el error de Nombre corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Per", "77777", "77777");
	}

	// PRN. Loguearse con exito desde el ROl de Usuario, 99999990D, 123456
	public void PR07d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	public void PR08d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	public void PR09d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	public void PR10d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	public void PR11d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		PO_HomeView.clickOption(driver, "logout", "class", "glyphicon glyphicon-log-out");
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse
	// usando el rol de estudiante.
	public void PR12d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 4);
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion
	// = Nota A2.
	// P13. Ver la lista de Notas.
	public void PR13d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Contamos las notas
		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/followingsibling::*[2]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Esperamos por la ventana de detalle
		PO_View.checkElement(driver, "text", "Detalles de la nota");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	// P14. Loguearse como profesor y Agregar Nota A2.
	// P14. Esta prueba podría encapsularse mejor ...
	public void PR14d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, "text", "99999993D");
		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de añadir nota: //a[contains(@href,
		// 'mark/add')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
		// Pinchamos en agregar Nota.
		elementos.get(0).click();
		// Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
		// Esperamos a que se muestren los enlaces de paginación la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página
		elementos.get(3).click();
		// Comprobamos que aparece la nota en la pagina
		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	// PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota
	// Nueva 1.
	// PRN. Ver la lista de Notas.
	public void PR15d() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, "text", "99999993D");
		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de notas.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'mark/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
		// Nos vamos a la última página
		elementos.get(3).click();
		// Esperamos a que aparezca la Nueva nota en la ultima pagina
		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
		// td[contains(text(), 'Nota Nueva
		// 1')]/following-sibling::*/a[contains(text(),'mark/delete')]"
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
		elementos.get(0).click();
		// Volvemos a la última pagina
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
		elementos.get(3).click();
		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
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