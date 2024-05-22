package rahulshettyacademy.TestComponents;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;


public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties(); //Creating object for the properties class
		
		/*We want to load the GlobalData.properties file in prop.load() method, 
		 * But it is expecting it as a stream,
		 * Now we need to convert the file into stream by using the FileInputStream class.  
		 * Create an object (fis) for FileInputStream class and send the GlobalData.properties file path as argument.  
		 * Now fis object will convert the properties file to inputstream object.  
		 * Once this is set it is easy to extract any value from the properties file.
		 * In the below file path is too long, until this path (C:\\Users\\vns24\\eclipse-workspace\\SeleniumFrameworkDesign07Apr2024)it is referring to you local system, 
		 * To dynamically get your local system path, which can be used in other systems too use (System.getProperty("user.dir")) */
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		prop.load(fis);
		
		/*To configure the browser properties run time during maven execution: 
		 * Currently we are getting the property from the String browserName = prop.getProperty("browser"); properties file
		 * To read the property received from maven, use System.getProperty this will read the system level properties*/
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		/* getting the browser property using getProperty method and storing it in a variable*/
		//String browserName = prop.getProperty("browser");
		/*Using the if condition we are setting the browser and executing the scripts for the browser*/
		if(browserName.contains("chrome")){
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup(); 
			if(browserName.contains("headless")){
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension (1440, 900));
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup(); 
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup(); 
			driver = new EdgeDriver();
		}
		/* Below code does not know driver, because the scope of the driver is within the if condition.  
		 * To solve this create global variable (public WebDriver driver;)  
		 * Whatever object created now will be assigned to the global variable.  
		 * Now the global variable will have access to everywhere inside this class.
		 *  WebDriver can be removed from the driver object creation.
		 *  Return the driver to this method, which has everything like browser details, maximize & timeouts*/
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// Reading the json to string
		// As readFileToString is deprecated use the StandardCharsets.UTF_8
		//Create a string variable filePath and send the file path from the test ie. SubmitOrderTest -> getData method:
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);

		// Convert String to Hashmap using jackson databind (get the dependency from
		// maven repository and add it in pom.xml)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	 public LandingPage launchApplication() throws IOException
	 {
		driver = initializeDriver();
		/*Move the below code from SubmitOrderTest and update the launchApplication() in the SubmitOrderTest
		 	in place of the below code
		 * Extend the base test (SubmitOrderTest extends BaseTest) so that it can inherit the all the methods
		 	from the BaseTest class
		 * Remove the public static void main(String[] args) in the SubmitOrderTest and use the testNG 
		 	annotation @Test
		 * Create the method public void submitOrder() under @Test testNG annotation in SubmitOrderTest
		 * As landing page object is created here in this class we need to return the landingPage object
		 	so that it can be used in the SubmitOrderTest class submitOrder method
		 * Create a variable for landingPage in SubmitOrderTest LandingPage landingPage = launchApplication();*/
		 landingPage = new LandingPage(driver);
		landingPage.goTo();
		 /* Instead of creating object for ProductCatalogue in this class we can add the object creation code 
		  in LandingPage class loginApplication method and return the productCatalogue object*/
		return landingPage;
		 
	 }
	
	@AfterMethod (alwaysRun = true)
	public void wrapDown()
	{
		driver.close();
	}

}
