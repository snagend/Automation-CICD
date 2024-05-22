package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ChkOutPage;
import rahulshettyacademy.pageobjects.CnfrmtnPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException {

		landingPage.loginApplication("govinda@gmail.com", "Thunde143");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}

	@Test
	public void productErrorValidation() throws IOException {

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("vns1@gmail.com", "Thunder@143");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
