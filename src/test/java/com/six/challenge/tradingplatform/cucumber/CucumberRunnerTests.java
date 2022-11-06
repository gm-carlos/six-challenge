package com.six.challenge.tradingplatform.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = {"src/test/resources/features/Test.feature"},
        glue = {"com.six.challenge.tradingplatform.cucumber"},
        plugin = {"pretty"})
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}
