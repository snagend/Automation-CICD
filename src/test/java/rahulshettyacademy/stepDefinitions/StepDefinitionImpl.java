package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ChkOutPage;
import rahulshettyacademy.pageobjects.CnfrmtnPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest {
	//Inherit the BaseTest class.
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CnfrmtnPage cnfrmtnPage;
	@Given ("I landed on Ecommerce page") //first step from SubmitOrder.feature file
	
	//use the same step name as method name with underscore
	public void I_landed_on_Ecommerce_page() throws IOException 
	{
		/* As launchApplication method returns landing page object catch it in a variable*/
		landingPage = launchApplication(); 
	}
	
	/*In regular expressions, the dot (.) matches any single character except newline characters. 
	 The + is a quantifier that means "one or more" of the preceding element.  So, .+ in the context 
	 of your regular expression ^Logged in with username (.+) and password (.+)$ means: (.+): Capture 
	 group matching one or more of any character (except newline). The parentheses denote a capturing group, 
	 allowing you to extract this part of the string later. The + ensures that there is at least one 
	 character present.  Therefore, (.+) would match any sequence of characters (except newline) that 
	 is at least one character long, and it would capture this sequence for later use in your testing or 
	 automation scenario. */
	
	@Given ("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When ("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When ("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName)
	{
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		ChkOutPage chkOutPage = cartPage.goToCheckout();
		chkOutPage.selectCountry("india");
		cnfrmtnPage = chkOutPage.submitOrder();
		}
	
	@Then ("{string} message is displayed on Confirmation page")
	public void message_displayed_Confirmationpage(String string)
	{
		String confirmMessage = cnfrmtnPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
	}
	
	@Then("^\"([^\"]*)\" message is displayed $")
	public void something_message_is_displayed(String strArg1)
	{
		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		driver.close();
	}
	

}
