package com.exadel.automation.pojo.helper;


import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.Elements;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;


/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class ComponentElementChecker extends TestBase {
    WebDriver driver;


    public ComponentElementChecker(WebDriver driver) {
        this.driver = driver;
    }

    public String checkElementLook(Elements element) {
        StringBuilder result = new StringBuilder();
        WebElementHelper webElementHelper = new WebElementHelper(driver);
        WebElement elementToCheck = webElementHelper.getChildWebelement(element);
        result.append(webElementHelper.checkFontSize(elementToCheck, element))
                .append(webElementHelper.checkImageCanBeLoaded(elementToCheck))
                .append(webElementHelper.CheckImageRenditions(elementToCheck, element));
        return result.toString();
    }
}
