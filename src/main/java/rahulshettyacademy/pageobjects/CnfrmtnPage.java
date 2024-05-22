package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CnfrmtnPage extends AbstractComponent{

	WebDriver driver;
	public CnfrmtnPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(className="hero-primary")
	WebElement confirmationMessage;
	
	public String getConfirmationMessage()
	{
		return confirmationMessage.getText();
	}


}
