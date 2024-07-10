package org.mifos.integrationtest;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CourgetteTestOutput;
import courgette.api.CucumberOptions;
import courgette.api.junit.Courgette;
import org.junit.runner.RunWith;

//@RunWith(Cucumber.class)
//@CucumberOptions(features = { "src/test/java/resources" }, glue = { "org.mifos.integrationtest.cucumber" }, plugin = {
//        "html:cucumber-report", "json:cucumber.json", "pretty", "html:build/cucumber-report.html", "json:build/cucumber-report.json",
//        "junit:build/cucumber.xml" })

@RunWith(Courgette.class)
@CourgetteOptions(threads = 3, runLevel = CourgetteRunLevel.FEATURE, rerunFailedScenarios = false, testOutput = CourgetteTestOutput.CONSOLE, generateCourgetteRunLog = true, reportTitle = "Paymenthub Test results", reportTargetDir = "build", cucumberOptions = @CucumberOptions(features = "src/test/java/resources", glue = "org.mifos.integrationtest.cucumber", publish = true, plugin = {
        "pretty", // Pretty console output
        "html:build/cucumber-report.html", // HTML report
        "json:build/cucumber-report.json", // JSON report
        "junit:build/cucumber.xml" // JUnit XML report
}))
@SuppressWarnings({ "FinalClass", "HideUtilityClassConstructor" })
public class TestRunner {

}
