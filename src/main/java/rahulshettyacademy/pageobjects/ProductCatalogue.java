package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sun.org.apache.xpath.internal.operations.Variable;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	
	WebDriver driver; 
	
	public ProductCatalogue(WebDriver driver) 
	{
		super(driver);
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".mb-3") 
	List<WebElement> products; //as it is a findelements we need to put it as List<WebElement>
	
	@FindBy(css = ".ng-animating") 
	WebElement spinner;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		/* as we are not using the webelement, we are using by locator in this case we need to use By productsBy
		 * and the productsBy variable will be passed as argument to waitForElementToAppear method.
		 */
		return products;  
		/* products are coming from the above FindBy annotation using the css = >mb-3 products
		are loaded to products Variable.class */
	}
	
	public WebElement getProductByName(String productName)
	{
		//productName is being passed from the SubmitOrderTest - productName variable.
		/* products.stream() will be converted to getProductList().stream() because in the previous step products
		 * are being returned to getProductList method.
		 */
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.xpath("//div/h5/b")).getText().equals(productName))
				.findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) {
		/*productName is returned in the getProductByName method and is being captured in productName variable 
		 * in the current addProductToCart method the product name is being captured in the prod variable*/
		 WebElement prod = getProductByName(productName);
		
		 /* In the below code as we are not using driver.findElement and the driver scope is limited to prod
		 *  we cannot use the @FindBy pagefactory, instead we can use the by locator */
		prod.findElement(addToCart).click();
		/*reuse the method from abstract component class and create a variable for by locator and update the same:*/
		waitForElementToAppear(toastMessage);
		//As the below wait is of webelement not by locator we can use the pagefactory @FindBy
		waitForElementtoDisappear(spinner);
	}
	
	
}
