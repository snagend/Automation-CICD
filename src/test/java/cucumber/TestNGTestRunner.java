package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*CucumberOptions - Helps to specify the option to execute the test cases
 *features="src/test/java/cucumber" - Location of the feature file 
 *glue="rahulshettyacademy.stepDefinitions" - location of the stepdifinition file
 *monochrome = true - Generates the report in readable format
 *tags = "Regression" - Runs only the scenario which has @Regression tag name
 *plugin = {"html:target/cucumber.html"} - Generates html report and saves in target folder*/

@CucumberOptions(features="src/test/java/cucumber", glue="rahulshettyacademy.stepDefinitions", 
monochrome = true, tags = "@Regression", plugin = {"html:target/cucumber.html"})

/*Cucumber does not have an idea how to run the testNG files, for that testNG has provided a class
 	called AbstractTestNGCucumberTests once inheriting this class your runner wll have an idea to
 	execute the testNG commands
 * If you are running your tests in junit then AbstractTestNGCucumberTests class is not required,
     because cucumber has an ability to the junit code*/
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

}
