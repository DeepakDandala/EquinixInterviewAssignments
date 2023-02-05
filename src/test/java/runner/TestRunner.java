package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", dryRun = false, glue = { "stepDefs" }, plugin = {
		"json:target/cucumber.json" }, tags = "@regression")
public class TestRunner {

}