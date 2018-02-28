package com.hpe.automation;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

import java.io.IOException;

public class WebDriverManager {

    public static void setupWebDriver(SuiteConfiguration suiteConfiguration) throws IOException {
        switch (suiteConfiguration.getCapabilities().getBrowserName()) {
            case "firefox":
                FirefoxDriverManager.getInstance().version(suiteConfiguration.getProperty("firefox-driver.version")).setup();
                break;
            case "MicrosoftEdge":
                EdgeDriverManager.getInstance().version(suiteConfiguration.getProperty("edge-driver.version")).setup();
                break;
            case "internet explorer":
                InternetExplorerDriverManager.getInstance().version(suiteConfiguration.getProperty("ie-driver.version")).arch32().setup();
                break;
            case "chrome":
            default:
                ChromeDriverManager.getInstance().version(suiteConfiguration.getProperty("chrome-driver.version")).setup();
        }
    }
}
