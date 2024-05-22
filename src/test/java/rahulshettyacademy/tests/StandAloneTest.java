package rahulshettyacademy.tests;

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

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();  //Webdrivermanager will download the chromedriver to your local system
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.findElement(By.id("userEmail")).sendKeys("govinda@gmail.com");
		driver.findElement(By.cssSelector("input[formcontrolname='userPassword']")).sendKeys("Thunder@143");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
//Adding items to cart using for loop:
	/*	for(int i=0; i<products.size(); i++)
		{
			if(products.get(i).getText().contains("ZARA COAT 3"))
			{
				driver.findElement(By.cssSelector(".btn.w-10.rounded")).click();
			}
		} */
		
//Adding items to cart using java streams:
		
/*	While using the streams, if you face any compiler error --> Go to project properties >> Java Compiler >> 
	under JDK compliance >> unselect "use compliance from execution environment 'JavaSE-1.7' on the java build path" 
	and select 1.8 from the dropdown. */
	
/*	In the list of products we got from findelements in previous step, we are applying streams, now the product
	has the first product, here we are limiting the webdriver scope to product block and by using xpath we are 
	getting text of each product and comparing the ZARA COAT 3 and getting the first product, if the product
	is not available then stream will return null.*/
	WebElement prod = products.stream()
			.filter(product -> product.findElement(By.xpath("//div/h5/b")).getText().equals(productName))
			.findFirst().orElse(null);
	
/*In the below code we have the product details of "ZARA COAT 3" in the prod WebElement, by controlling the 
 * webdriver scope to prod by using the css selector in the card-body block which contains "Add to cart" button 
 * we are clicking on the button. As card-body has two buttons we are using "button:last-of-type" locator, which
 * will identify the last button and click.
 */
	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	
//waiting till the display of "Product added to card" toast message:
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	
//Waiting until the invisibility of animation (status updater):
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
//clicking on the cart button:
	driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
/* using the CSS selector getting the cart item name (.classname tagname) and using streams verifying the 
	selected item is present in the cart*/
	List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
	Assert.assertTrue(match);
	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".totalRow button")));
	driver.findElement(By.cssSelector(".totalRow button")).click();
	
//Sending the country name using the actions class, this can be done through driver.findelements sendkeys method also.
	Actions a = new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')]) [2]")).click();
	driver.findElement(By.cssSelector(".action__submit")).click();
	String confirmMessage = driver.findElement(By.className("hero-primary")).getText();
	Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	driver.close();

	}

}
