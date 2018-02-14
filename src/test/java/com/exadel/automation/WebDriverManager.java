package com.exadel.automation;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
