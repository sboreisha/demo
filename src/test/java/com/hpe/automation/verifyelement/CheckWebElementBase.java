package com.hpe.automation.verifyelement;


import com.hpe.automation.TestBase;
import com.hpe.automation.listeners.AllureListener;
import com.hpe.automation.listeners.JiraListener;
import com.hpe.automation.pojo.ComponentSelector;
import com.hpe.automation.pojo.ElementSelector;
import com.hpe.automation.pojo.Elements;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class CheckWebElementBase extends TestBase {
    protected WebDriver driver;
    public By videoOverlayBy = By.id("video-overlay");


    /*
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public CheckWebElementBase(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Builds webdriver by based on json component selector
     *
     * @param componentSelector
     * @return
     */
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

    /**
     * Builds element by base on json element
     *
     * @param elementSelector
     * @return
     */
    public By getElementBy(ElementSelector elementSelector) {
        return getElementBy(new ComponentSelector(elementSelector.getHow(), elementSelector.getSelector()));
    }

    /**
     * Search for Web Element based on JSON element
     *
     * @param element
     * @return
     * @throws NoSuchElementException
     */
    public WebElement getWebelementByPageElement(Elements element) throws NoSuchElementException {
        return getElement(getElementBy(element.getElementSelector()));
    }

    /**
     * Get web element by webdriver By
     *
     * @param by
     * @return
     * @throws NoSuchElementException
     */
    public WebElement getElement(By by) throws NoSuchElementException {
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
            return driver.findElement(by);
        }
    }

    /**
     * Checks font family of the element
     *
     * @param webElement
     * @param fontFamily expected font family
     * @return
     */
    public String checkFontFamily(WebElement webElement, String fontFamily) {
        String actualSize = webElement.getCssValue("font-family");
        actualSize = actualSize.replace("\"", "");
        if (actualSize.contains(fontFamily)) {
            return "+ Font family is ok \n";
        } else {
            return failedStepLog("- Expected font family " + fontFamily + ", but got " + actualSize + "\n");
        }
    }

    /**
     * Checks text align
     *
     * @param webElement
     * @param textAlign  expected align
     * @return
     */
    public String checkTextAlign(WebElement webElement, String textAlign) {
        String actualSize = webElement.getCssValue("text-align");
        actualSize = actualSize.replace("\"", "");
        if (actualSize.contains(textAlign)) {
            return "+ Text align is is ok \n";
        } else {
            return failedStepLog("- Expected text align to be " + textAlign + ", but got " + actualSize + "\n");
        }
    }

    /**
     * Checks font color in hex
     *
     * @param webElement
     * @param hexColor
     * @return
     */
    public String checkFontColor(WebElement webElement, String hexColor) {
        String actualColor = webElement.getCssValue("color");
        if ((rgbToHex(actualColor)).contains(hexColor)) {
            return "+ Font color is ok \n";
        } else {
            return failedStepLog("- Expected font color " + hexColor + ", but got " + rgbToHex(actualColor) + "\n");
        }
    }

    /**
     * Checks line height of the element
     *
     * @param webElement
     * @param expectedSize
     * @return
     */
    public String checkLineHeight(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("line-heigh");
        if ((rgbToHex(actualSize)).contains(expectedSize)) {
            return "+ Lin e height \n";
        } else {
            return failedStepLog("- Expected height " + expectedSize + ", but got " + rgbToHex(actualSize) + "\n");
        }
    }

    /**
     * Checks font size of text element
     *
     * @param webElement
     * @param expectedSize
     * @return
     */
    public String checkFontSize(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("font-size");
        if (expectedSize.equalsIgnoreCase(actualSize)) {
            return "+ Font size is ok \n";
        } else {
            return failedStepLog("- Expected font size " + expectedSize + ", but got " + actualSize + "\n");
        }
    }

    /**
     * Checks cursor style for web element
     *
     * @param webElement    webelement to check
     * @param expectedStyle e.g. pointer or auto
     * @return
     */
    public String checkCursorStyle(WebElement webElement, String expectedStyle) {
        String actualSize = webElement.getCssValue("cursor");
        if (expectedStyle.equalsIgnoreCase(actualSize)) {
            return "+ Cursor style is OK \n";
        } else {
            return failedStepLog("- Expected cursor style " + expectedStyle + ", but got " + actualSize + "\n");
        }
    }

    /**
     * Checks if provided data attribute is present in element
     *
     * @param element element to check
     * @param value   data attribute to check
     * @return
     */
    public String checkDataAttribute(WebElement element, String value) {
        String actualName = getAttributesByPartialName(element, "data");
        if (actualName.contains(value)) {
            return "+ Data attribute value " + value + " is ok\n";
        }
        return failedStepLog("- Expected data attribute to be " + value + ", but got " + actualName + "\n");
    }

    /**
     * Checks all attributes with data prefix value
     *
     * @param element element to check
     * @param value   coma separated list of expected attributes
     * @return not matched attributes
     */
    public String checkAllDataAttributes(WebElement element, String value) {
        String[] expectedArray = value.split(",");
        String[] actualArray = getAttributesByPartialName(element, "data").replace("{", "").replace("}", "").split(",");
        ArrayList expectedValues = new ArrayList<String>(Arrays.asList(expectedArray));
        ArrayList actualValues = new ArrayList<String>(Arrays.asList(actualArray));
        expectedValues.removeAll(Arrays.asList(actualArray)); //list contains items only in name
        actualValues.removeAll(Arrays.asList(expectedArray)); //list2 contains items only in name2
        actualValues.addAll(expectedValues); //list2 now contains all the not-common items

        if (actualValues.size() == 0) {
            return "+ All data attributes are ok\n";
        }
        return failedStepLog("- Invalid attribute values:" + actualValues.toString() + "\n");

    }

    /**
     * Checks all analytics attribute
     *
     * @param element webelement to check
     * @param value   coma separated analytics attribute with values
     * @return
     */
    public String checkAllAnalytics(WebElement element, String value) {
        String[] expectedArray = value.split(",");
        String[] actualArray = getAttributesByPartialName(element, "analytics").replace("{", "").replace("}", "").split(",");
        ArrayList expectedValues = new ArrayList<String>(Arrays.asList(expectedArray));
        ArrayList actualValues = new ArrayList<String>(Arrays.asList(actualArray));
        expectedValues.removeAll(Arrays.asList(actualArray)); //list contains items only in name
        actualValues.removeAll(Arrays.asList(expectedArray)); //list2 contains items only in name2
        actualValues.addAll(expectedValues); //list2 now contains all the not-common items

        if (actualValues.size() == 0) {
            return "+ All analytics are ok " + value + " is ok\n";
        }
        return failedStepLog("- Invalid analytics values:" + actualValues.toString() + "\n");

    }

    /**
     * Checks background color of element
     *
     * @param webElement
     * @param hexColor
     * @return
     */
    public String checkBgColor(WebElement webElement, String hexColor) {
        String str = webElement.getCssValue("background-color");
        if (hexColor.equalsIgnoreCase(rgbToHex(str))) {
            return "+ Caption wrapper background color is ok \n";
        } else {
            return failedStepLog("- Expected caption wrapper background color to be " + hexColor + ", but got " + rgbToHex(str) + "\n");
        }
    }


    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } // try
        catch (Exception e) {
            return false;
        } // catch
    }

    /**
     * Checks if there are more then 1 window/tab opened, closes secondary window
     *
     * @return
     */
    public Boolean isNewWindowOpened() {
        Boolean isOpened = false;
        String winHandleBefore = driver.getWindowHandle();
        if (driver.getWindowHandles().size() > 1) {
            isOpened = true;

            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            driver.close();
            driver.switchTo().window(winHandleBefore);
        }
        return isOpened;
    }


    /**
     * Gets all webelent's attributes with partial name
     *
     * @param element
     * @param partialName
     * @return
     */
    private String getAttributesByPartialName(WebElement element, String partialName) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object aa = executor.executeScript("var items = {}; " +
                "var substring=\"" + partialName + "\";" +
                "for (index = 0; index < arguments[0].attributes.length; ++index) " +
                "{if(arguments[0].attributes[index].name.includes(substring)){" +
                " items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }}; " +
                "return items;", element);
        return aa.toString();
    }

    /**
     * Converts RGB color to HEX color
     *
     * @param color
     * @return
     */
    private String rgbToHex(String color) {
        String[] str2 = color.split(",");
        return String.format("#%02X%02X%02X", Integer.valueOf(str2[0].replaceAll("\\D+", ""))
                , Integer.valueOf(str2[1].replaceAll("\\D+", ""))
                , Integer.valueOf(str2[2].replaceAll("\\D+", "")));
    }

    /**
     * Checks if video timer is present for element
     *
     * @param element
     * @return
     */
    public String checkVideoInfoTimer(WebElement element) {
        if (element.findElements(By.cssSelector(".video-info")).size() > 0) {
            return "+ Video info is present";
        }
        return failedStepLog("- Video info is not displayed\n");
    }

    public String checkNewWindowOnHyperlink(WebElement element) {
        try {
            WebElement webElement = element.findElement(By.cssSelector("a"));
            webElement.click();
            if (isNewWindowOpened()) {
                return "+ New window is opened on hyperlink\n";
            }
            Uninterruptibles.sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
            driver.navigate().back();
            return failedStepLog("- Window is not opened on hyperlink\n");
        } catch (Exception e) {
            Uninterruptibles.sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
            driver.navigate().back();
            Uninterruptibles.sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
            return failedStepLog("- something went wrong with new window on hyperlink \n");
        }
    }

    public String checkNoNewWindowOnHyperlink(WebElement element) {
        try {
            String urlBefore = driver.getCurrentUrl();
            WebElement webElement = element.findElement(By.cssSelector("a"));
            webElement.click();
            Uninterruptibles.sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
            if (!urlBefore.equals(driver.getCurrentUrl())) {
                driver.navigate().back();
                return "+ Browser navigates to hyperlink\n";
            }
            return failedStepLog("- Link navigation is not working\n");
        } catch (Exception e) {
            return failedStepLog("- something went wrong with no new window on hyperlink\n");
        }
    }

    public boolean checkElementIsPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkElementIsPresent(Elements element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(getElementBy(element.getElementSelector())));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void pressEsc() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
    }

    public void pressSpace() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SPACE);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
    }
}
