package stepDefnitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import junit.framework.Assert;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException 
	{
		configProp = new Properties();
		FileInputStream configpropfile = new FileInputStream("config.properties");
		configProp.load(configpropfile);
		
		//logger=Logger.getLogger("nopCommerce");//Added logger
		//PropertyConfigurator.configure("Log4j.properties");
	    
		String br = configProp.getProperty("browser");
		
		if(br.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
			driver= new ChromeDriver();
		} else 
		
			if(br.equalsIgnoreCase("firefox"))
		{
				System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
				driver= new FirefoxDriver();
		}
		
		
		//logger.info("**************Launching Browser*********");
		
	}
	
	
	@Given("User Launch chrome browser")
	public void user_Launch_chrome_browser() {
		
	    lp= new LoginPage(driver);
	}

	@When("User opens URL {string}")
	public void user_opens_URL(String url) {
		//logger.info("opening URL----------");
	    driver.get(url);
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_Email_as_and_Password_as(String email, String password) {
	 //logger.info("************provided login details*****");
	 lp.setUserName(email);
	 lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_Login() throws InterruptedException {
		//logger.info("*******started login****");
	    lp.clickLogin();
	    Thread.sleep(3000);
	}

	@Then("Page Title should be {string}")
	public void page_Title_should_be(String title) {
	    if (driver.getPageSource().contains("Login was unsuccessful")) {
			driver.close();
			Assert.assertTrue(false);
		} else {
			Assert.assertEquals(title, driver.getTitle());
		}
	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException  {
	    lp.clickLogout();
	    Thread.sleep(3000);
	}

	@Then("close browser")
	public void close_browser() {
	   driver.close();
	}
	
	//Customers feature step defnitions-----------------------------------------------------------------------------
	
	@Then("User can view Dashboard")
	public void user_can_view_Dashboard() {
		
		addCust = new AddCustomerPage(driver);
		Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
			   
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_Menu() throws InterruptedException {
	    Thread.sleep(3000);
		addCust.clickOnCustomerMenu();
	}

	@When("click on customers Menu Item")
	public void click_on_customers_Menu_Item() throws InterruptedException {
		Thread.sleep(3000);
		addCust.clickOnCustomersMenuItem();
	}

	@When("click on Add new button")
	public void click_on_Add_new_button() throws InterruptedException {
	   	addCust.clickOnAddnew();
	    Thread.sleep(2000);
	}

	@Then("user can view Add new customer page")
	public void user_can_view_Add_new_customer_page() {
	   Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User enter customer info")
	public void user_enter_customer_info() throws InterruptedException  {
	   Thread.sleep(2000);
		String email = randomestring() + "@gmail.com";
	   addCust.setEmail(email);
	   addCust.setPassword("test123");
	   	//Registered - default
	   //The customer cannot be in both 'Guests' and 'Registered' customer roles
	   //Add the customer to 'Guests' or 'Registered' customer role
	  // addCust.clearcustomerrole();
	   //Thread.sleep(1000);
	   addCust.clearcustomerrole();
	   Thread.sleep(2000); 
	   addCust.setCustomerRoles("Guests");
	   
	   
	   addCust.setManagorOfVendor(1);
	   addCust.setGender("Male");
	   addCust.setFirstName("pavan");
	   addCust.setLastName("kumar");
	   addCust.setDob("7/05/1985");//Format : d/mm/yy
	   addCust.setCompanyName("busyQA");
	   addCust.setAdminContent("This is for testing.............");
	   
	}

	@When("click on save button")
	public void click_on_save_button() throws InterruptedException {
	    addCust.ClickonSave();
	    Thread.sleep(2000);
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String msg) {
		Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
				.contains("The new customer has been added successfully"));
		    
	}
	
	//searching a customer using a email id
	
	@When("Enter customer Email")
	public void enter_customer_Email() {
		searchCustomer = new SearchCustomerPage(driver);
		searchCustomer.clickSearch();
		searchCustomer.setEmail("victoria_victoria@nopCommerce.com");
	}

	@When("Click on search button")
	public void click_on_search_button() throws InterruptedException {
		searchCustomer.clickSearch();
		Thread.sleep(3000);
	}

	@Then("User should found Email in the Search table")
	public void user_should_found_Email_in_the_Search_table() {
	    
		boolean status=searchCustomer.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
		
		Assert.assertEquals(true, status);
	}

	
	// Searching customer bt using firstname and lastname
	
	@When("Enter customer FirstName")
	public void enter_customer_FirstName() {
		searchCustomer = new SearchCustomerPage(driver);
		searchCustomer.setFirstName("Victoria");
		   
	}

	@When("Enter customer LastName")
	public void enter_customer_LastName() {
		searchCustomer.setLastName("Terces");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_Name_in_the_Search_table() {
	    boolean status = searchCustomer.searchCustomerByName("Victoria Terces");
	    Assert.assertEquals(true, status);
	}
}



	
