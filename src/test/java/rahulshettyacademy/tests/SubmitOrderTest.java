package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ChkOutPage;
import rahulshettyacademy.pageobjects.CnfrmtnPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException {

		

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

		// Using the productCatalogue object call the getProductList method and save the
		// output to List<WebElement> products
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		ChkOutPage chkOutPage = cartPage.goToCheckout();
		chkOutPage.selectCountry("india");
		CnfrmtnPage cnfrmtnPage = chkOutPage.submitOrder();
		String confirmMessage = cnfrmtnPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("govinda@gmail.com", "Thunder@143");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
		
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		/*Create a multi-dimensional array and return it
		 * Object[] [] represents two sets of data
		 * Sending the email password and products two sets
		 * Object refers to parent datatype, through which we can send any datatypes like (int, string, etc
		 * After defining the variable in the array, we neen to attach the same to the test submitOrder*/
	/*	HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "govinda@gmail.com");
		map.put("password", "Thunder@143");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "vns1@gmail.com");
		map1.put("Password", "Thunder@143");
		map1.put("product", "ADIDAS ORIGINAL"); */
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		//there are two lists (index 0 & 1) in the data variable, to retrieve the objects 
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
	

}

