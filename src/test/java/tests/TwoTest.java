package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import resources.Base;

public class TwoTest extends Base{
	
	public WebDriver driver;

	@Test
	public void twotest() throws IOException, InterruptedException {
		
		System.out.println("Two");
		driver = intializeDriver();
		driver.get("http://tutorialsninja.com/demo/");
		Thread.sleep(3000);
		driver.close();
	}

}
