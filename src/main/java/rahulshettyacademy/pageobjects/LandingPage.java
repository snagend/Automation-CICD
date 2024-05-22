package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	WebDriver driver; //local variable driver created, which is used only in this class 

	/*Below constructor has reference to SubmitOrderTest class in which object is created for LandingPage class
	 * from there the details of driver object is being sent to the LandingPage class*/
	public LandingPage(WebDriver driver) 
	{
		super(driver);
	/* this.driver denotes local driver variable and =driver denotes the driver object from the SubmitOrderTest class*/
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	/* driver object from SubmitOrderTest is sent to LandingPage constructor and then it has been casted to 
	 * driver variable of this class using this.class.  Now the driver object has the details of the SubmitOrderTest class*/
	
	/* WebElement userEmail = driver.findElement(By.id("userEmail")); --> This can be written in this way using
	 * page object or to simplify this we can use PageFactory model in the page objects as below.
	 */
	
	@FindBy(id = "userEmail") /*using the id locator with value userEmail we are identifying the locator, 
	this will be converted to driver.findElement(By.id("userEmail")) uding PageFactory during runtime*/
	WebElement userEmail; //variable in which the userEmail element will be captured.
	
	@FindBy(css = "input[formcontrolname='userPassword']")
	WebElement passwordEle;
	
	@FindBy(xpath = "//input[@value='Login']")
	WebElement submit; 
	
	@FindBy(css = "div[aria-label='Incorrect email or password.']")
	WebElement errorMessage;
	
	
	/* Creating a action method loginApplication by providing email and password variable, the value for this
	 * variables will be passed from SubmitOrdertest class by calling the landingPage object loginApplication method
	 * user Email, passwordEle and submit are coming from @FindBy PageFactory annotation.*/
	public ProductCatalogue loginApplication(String email, String password)
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		/* In the LandingPage class loginApplication method we know that after clicking on the submit button 
		 * (submit.click()) it will navigate to Product catalogue page, instead of creating object for 
		 * ProductCatalogue class in the SubmitOrderTest class we can create the object in the loginApplication 
		 * method itself and then return the object in the loginApplication method.  In the SubmitOrderTest
		 * class pass that class and the object as below.*/
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
}
