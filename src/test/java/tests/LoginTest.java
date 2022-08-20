package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.Base;

public class LoginTest extends Base {
	
	Logger log;
	WebDriver driver;
	
	@Test(dataProvider="getLogindata")
	public void login(String email,String password,String expectedresult) throws IOException {
		
		
		
		LandingPage landingpage = new LandingPage(driver);
		landingpage.myAccountDropdown().click();
		log.debug("Clicked on My Account dropdown");
		landingpage.loginOption().click();
		log.debug("Clicked on login option");


		
		LoginPage loginpage = new LoginPage(driver);
		loginpage.EmailAddressField().sendKeys(email);
		log.debug("Email addressed got entered");

		loginpage.PasswordField().sendKeys(password);
		log.debug("Password got entered");

		loginpage.LoginButton().click();
		log.debug("Clicked on Login Button");

		
		AccountPage account = new AccountPage(driver);
		//Assert.assertTrue(account.editAccountOption().isDisplayed());
		//System.out.println(account.editAccountOption().isDisplayed());
		//these try catch block is used for exception handle
		String actualresult = null;
		try {
			
			if(account.editAccountOption().isDisplayed()){
				   log.debug("User got logged in");

				actualresult = "Sucess";
			}
		}
		catch(Exception e){
			log.debug("User didn't log in");

			actualresult = "failure";

		}
		Assert.assertEquals(actualresult,expectedresult);
		log.info("Login Test got passed");
		log.error("Login Test got failed");

	}
	
	@BeforeMethod
	public void openApplication() throws IOException {
		
		log = LogManager.getLogger(LoginTest.class.getName());
		driver = intializeDriver();
		log.debug("Browser got launched");
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to application URL");
		
		
	}
	
	@AfterMethod
	public void closure() {
		log.debug("Browser got closed");

		driver.close();
	}
    //we have multiple set of data we should not use data.properties we use @DataProvider only
	@DataProvider
	public Object[][] getLogindata() {
		
		Object[][] data = {{"vamsi12345@gmail.com","@123","Sucess"}/*,{"demmy@gmail","demmy","failure"}*/};
		return data;
	}
}
