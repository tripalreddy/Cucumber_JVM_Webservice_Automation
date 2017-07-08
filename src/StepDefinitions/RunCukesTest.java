package StepDefinitions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		//features="classpath:features\\ShoppingCart.feature", 
		features="classpath:features", 
		tags = {"@soap"},
		//features = "classpath:features",
				//format={"pretty","html:target/cucumber","json:target/cucumber.json"},
		//plugin = {"pretty", "html:target/cucumber-html-report"},
		plugin = {"pretty", "html:target/cucumber","json:target/cucumber.json"}
		
		)
public class RunCukesTest{
	
}