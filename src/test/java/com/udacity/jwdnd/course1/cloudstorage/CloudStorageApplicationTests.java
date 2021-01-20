package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	@Test
	public void getSignupAndLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}


	@Test
	public void testHomePageAccessibleBySignupLoginLogout(){
		String username = "pzastoup";
		String password = "whatabadpassword";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createViewEditAndDeleteNote() throws Exception{
		//Signup and Login
		String username = "pzastoup";
		String password = "whatabadpassword";
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//Create Note
		String noteTitle="Note Title";
		String noteDescription = "Note Description";
		HomePage homePage = new HomePage(driver);
		homePage.createNote(driver, noteTitle, noteDescription);

		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		//View Note
		driver.get(baseURL + "/home");
		Note firstNote = homePage.getFirstNote(driver);
		Assertions.assertEquals(noteTitle, firstNote.getNoteTitle());
		Assertions.assertEquals(noteDescription, firstNote.getNoteDescription());

		//Edit Note
		homePage.editNote(driver, "New Note Title", "New Note Description");
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));
		driver.get(baseURL + "/home");
		firstNote = homePage.getFirstNote(driver);
		Assertions.assertEquals("New Note Title", firstNote.getNoteTitle());
		Assertions.assertEquals("New Note Description", firstNote.getNoteDescription());

		//Delete Note
		boolean noteDeleted = homePage.deleteNote(driver);
		Assertions.assertEquals(noteDeleted, true);

	}

	@Test
	public void createViewEditAndDeleteCredential() throws Exception{
		//Signup and Login
		String username = "pzastoup";
		String password = "whatabadpassword";
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		/*-- Create credential --*/
		String url="google.com";
		String credentialUsername="pzastoup";
		String credentialPassword="pzagoogle";
		HomePage homePage = new HomePage(driver);
		homePage.createCredential(driver, url, credentialUsername, credentialPassword);

		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		/*-- View added credential --*/
		driver.get(baseURL + "/home");
		Credential firstCredential = homePage.getFirstCredential(driver);
		String encryptedPassword = firstCredential.getPassword();
		Assertions.assertEquals(url, firstCredential.getUrl());
		Assertions.assertEquals(credentialUsername, firstCredential.getUsername());
		Assertions.assertNotEquals(credentialPassword, firstCredential.getPassword());

		/*-- Edit credential --*/

		//View unencrypted credential
		homePage.viewCredential(driver);
		Assertions.assertEquals(credentialPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
		//Edit credential with a change in password
		homePage.saveCredential(driver,"google.com", "pzastoup","newgooglepassword");
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		driver.get(baseURL + "/home");
		firstCredential = homePage.getFirstCredential(driver);
		Assertions.assertEquals(url, firstCredential.getUrl());
		Assertions.assertEquals(credentialUsername, firstCredential.getUsername());
		//Encrypted password != previous encrypted password
		Assertions.assertNotEquals(encryptedPassword, firstCredential.getPassword());

		/*-- Delete credential --*/
		boolean credentialDeleted = homePage.deleteCredential(driver);
		Assertions.assertEquals(credentialDeleted, true);
	}
}
