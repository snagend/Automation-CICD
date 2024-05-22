package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".cartSection h3")
	List<WebElement> cartProducts;
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;
	public ChkOutPage goToCheckout()
	{
		checkoutEle.click();
		return new ChkOutPage(driver);
	}
	
	
	
	

}
