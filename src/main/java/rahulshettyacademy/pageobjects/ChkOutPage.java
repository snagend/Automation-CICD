package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ChkOutPage extends AbstractComponent {

	WebDriver driver;

	public ChkOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private By results = By.cssSelector(".ta-results");

	

	@FindBy(css = "[placeholder='Select Country']")
	private WebElement country;

	@FindBy(xpath = "(//button[contains(@class,'ta-item')]) [2]")
	private WebElement selectCountry;

	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();

		waitForElementToAppear(results);
		selectCountry.click();
	}

	@FindBy(css = ".action__submit")
	private WebElement submit;

	public CnfrmtnPage submitOrder() {
		submit.click();
		return new CnfrmtnPage(driver);
	}

}
