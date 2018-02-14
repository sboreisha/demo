package com.exadel.automation.pojo.helper;


import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.ComponentSelector;
import com.exadel.automation.pojo.Components;
import com.exadel.automation.pojo.ElementSelector;
import com.exadel.automation.pojo.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class WebElementHelper extends TestBase {
    protected WebDriver driver;

    /*
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public WebElementHelper(WebDriver driver) {
        this.driver = driver;
    }

    public By getElementBy(ComponentSelector componentSelector) {
        By by;
        String how = componentSelector.getHow();
        String selector = componentSelector.getSelector();
        switch (how) {
            case "id":
                by = By.id(selector);
                break; // optional
            case "className":
                by = (By.className(selector));
                break; // optional
            case "XPath":
                by = (By.xpath(selector));
                break; // optional
            case "cssSelector":
                by = (By.cssSelector(selector));
                break;
            default:
                by = null;
                // Statements
        }
        return by;
    }

    public By getElementBy(ElementSelector elementSelector) {
        return getElementBy(new ComponentSelector(elementSelector.getHow(), elementSelector.getSelector()));
    }

    public WebElement getChildWebelement(Components component, Elements element) {
        return getChildElement(getElementBy(component.getComponentSelector()), getElementBy(element.getElementSelector()));
    }

    public WebElement getChildWebelement(Elements element) {
        return getElement(getElementBy(element.getElementSelector()));
    }

    public WebElement getComponentWebelement(Components component) {
        return getElement(getElementBy(component.getComponentSelector()));
    }

    public WebElement getChildElement(By parent, By child) {
        return driver.findElement(parent).findElement(child);
    }

    public WebElement getElement(By by) {
        WebElement element;
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return driver.findElement(by);
        }
    }

    public Boolean checkElementPresence(By by) {
        try {
            getElement(by);
            return true;
        } catch (Exception e) {
            System.out.print("No element has been found by " + by);
            return false;
        }
    }

    public Boolean checkElementPresence(By parent, By child) {
        try {
            getChildElement(parent, child);
            return true;
        } catch (Exception e) {
            System.out.print("No child element has been found by " + parent + " " + child);
            return false;
        }
    }

    public String checkFontSize(WebElement webElement, Elements elements) {
        String expectedSize = elements.getElementCheckings().getFontSize();
        String actualSize = webElement.getCssValue("font-size");
        if (expectedSize.equalsIgnoreCase("-1") || expectedSize.equalsIgnoreCase(actualSize)) {
            return "+ Font size is ok \n";
        } else {
            return failedStepLog("- Expected font size " + expectedSize + ", but got " + actualSize + "\n");
        }
    }
}
