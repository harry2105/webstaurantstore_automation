package testMethods;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utilityFunctions.InitializeDriver;
import utilityFunctions.PropertiesConfig;
import utilityFunctions.ScreenShotCapture;

public class TestMethod {
	
	String textToSearch="";
	WebDriver driver;

	@BeforeSuite
	public void setup() throws IOException{
		PropertiesConfig pc=new PropertiesConfig();
		Properties p=pc.getProerty();
		 textToSearch=p.getProperty("textToSearch");
		InitializeDriver id=new InitializeDriver();		
		driver=id.getDriver();	

	}
	
	
	@Test
	public void test1() throws IOException, InterruptedException{

			InitializeDriver id=new InitializeDriver();		
			WebDriver driver=id.getDriver();		
			driver.get("https://www.webstaurantstore.com");
			driver.findElement(By.id("searchval")).sendKeys(textToSearch);
			driver.findElement(By.cssSelector(".btn.btn-info.banner-search-btn")).click();
			ScreenShotCapture.capture("SearchResult");
			List<WebElement> list=driver.findElements(By.xpath("//div[@id='product_listing']//a[@class='description']"));			
			for(WebElement element : list){				
				Assert.assertTrue(element.getText().contains("Table"));
			}
			
			List<WebElement> list_addButton=driver.findElements(By.xpath("//div[@id='product_listing']//input[@name='addToCartButton']"));
			
			//Click on add to Cart 
			list_addButton.get(list_addButton.size()-1).click();
			
			// Click on View Cart
			if(driver.findElement(By.xpath("//a[@class='btn btn-small btn-primary']")).isDisplayed()){
				driver.findElement(By.xpath("//a[@class='btn btn-small btn-primary']")).click();
			}
				driver.findElement(By.xpath("//span[@class='btn btn-small btn-primary']//span[@class='menu-btn-text']")).click();
		
			ScreenShotCapture.capture("CartPage");
			// Click on delete button
			driver.findElement(By.xpath("(//a[@class='deleteCartItemButton close'])[1]")).click();
			Thread.sleep(2000);
			//Verify empty cart
			String text=driver.findElement(By.xpath("//p[@class='header-1']")).getText();
			Assert.assertEquals(text, "Your cart is empty.");
			ScreenShotCapture.capture("EmptyCart");
		
	}

}
