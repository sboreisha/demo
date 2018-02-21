package com.exadel.automation.pojo.helper;


import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.ComponentSelector;
import com.exadel.automation.pojo.ElementSelector;
import com.exadel.automation.pojo.Elements;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.sun.webkit.network.URLs.newURL;

/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class CheckWebElementUI extends TestBase {
    protected WebDriver driver;
    By videoOverlayBy = By.id("video-overlay");
    By captionWrapperBy = By.cssSelector(".caption-wrapper");

    /*
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public CheckWebElementUI(WebDriver driver) {
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


    public WebElement getWebelementByPageElement(Elements element) throws NoSuchElementException {
        return getElement(getElementBy(element.getElementSelector()));
    }

    public WebElement getElement(By by) throws NoSuchElementException {
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            Uninterruptibles.sleepUninterruptibly(15000, TimeUnit.MILLISECONDS);
            return driver.findElement(by);
        }
    }


    public String checkFontFamily(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("font-family");
        actualSize = actualSize.replace("\"", "");
        if (actualSize.contains(expectedSize)) {
            return "+ Font family is ok \n";
        } else {
            return failedStepLog("- Expected font family " + expectedSize + ", but got " + actualSize + "\n");
        }
    }

    public String checkTextAlign(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("text-align");
        actualSize = actualSize.replace("\"", "");
        if (actualSize.contains(expectedSize)) {
            return "+ Text align is is ok \n";
        } else {
            return failedStepLog("- Expected text align to be " + expectedSize + ", but got " + actualSize + "\n");
        }
    }

    public String checkFontColor(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("color");
        if ((rgbToHex(actualSize)).contains(expectedSize)) {
            return "+ Font color is ok \n";
        } else {
            return failedStepLog("- Expected font color " + expectedSize + ", but got " + rgbToHex(actualSize) + "\n");
        }
    }


    public String checkFontSize(WebElement webElement, String expectedSize) {
        String actualSize = webElement.getCssValue("font-size");
        if (expectedSize.equalsIgnoreCase(actualSize)) {
            return "+ Font size is ok \n";
        } else {
            return failedStepLog("- Expected font size " + expectedSize + ", but got " + actualSize + "\n");
        }
    }

    public String checkImageCanBeLoaded(WebElement webElement) {
        String Source = webElement.getAttribute("currentSrc");
        try {
            BufferedImage img = ImageIO.read(newURL(Source));
            return "+ Image can be loaded";
        } catch (Exception e) {
            return failedStepLog("- Image can not be loaded \n");
        }
    }

    public String checkImageRenditions(WebElement webElement, String expectedSource) {
        String source = webElement.getAttribute("currentSrc");
        try {
            if (source.contains(expectedSource)) {
                return "+ Image rendition is ok";
            }
            return failedStepLog("- Image rendition is not OK. Expected " + expectedSource + " but got " + source + "\n");
        } catch (NullPointerException e) {

            return "";
        }
    }

    public String checkImageCursor(WebElement webElement, String expectedStyle) {
        WebElement wrapper = webElement.findElement(By.cssSelector(".image-wrapper"));
        return checkCursorStyle(wrapper, expectedStyle);
    }

    public String checkCaptionWrapperCursor(WebElement webElement, String expectedStyle) {
        WebElement wrapper = webElement.findElement(captionWrapperBy);
        return checkCursorStyle(wrapper, expectedStyle);
    }


    public String checkCursorStyle(WebElement webElement, String expectedStyle) {
        String actualSize = webElement.getCssValue("cursor");
        if (expectedStyle.equalsIgnoreCase(actualSize)) {
            return "+ Cursor style is OK \n";
        } else {
            return failedStepLog("- Expected cursor style " + expectedStyle + ", but got " + actualSize + "\n");
        }
    }

    public String checkAltText(WebElement webElement, String expectedStyle) {
        WebElement element = webElement.findElement(By.tagName("img"));
        String actualSize = element.getAttribute("title");
        if (expectedStyle.equalsIgnoreCase(actualSize)) {
            return "+ Popup text is ok \n";
        } else {
            return failedStepLog("- Expected popup text to be " + expectedStyle + ", but got " + actualSize + "\n");
        }
    }

    public String checkCaptionWrapperTextSize(WebElement webElement, String text) {
        WebElement captionWrapper = webElement.findElement(captionWrapperBy);
        return checkFontSize(captionWrapper, text);
    }

    public String checkCaptionWrapperFontStyle(WebElement webElement, String text) {
        WebElement captionWrapper = webElement.findElement(captionWrapperBy);
        return checkFontFamily(captionWrapper, text);
    }

    public String checkCaptionWrapperTextAlign(WebElement webElement, String text) {
        WebElement captionWrapper = webElement.findElement(captionWrapperBy);
        return checkTextAlign(captionWrapper, text);
    }

    public String checkDataAttribute(WebElement element, String value) {
        String actualName = getAttributesByPartialName(element, "data");
        if (actualName.contains(value)) {
            return "+ Data attribute value " + value + " is ok\n";
        }
        return failedStepLog("- Expected data attribute to be " + value + ", but got " + actualName + "\n");
    }

    public String checkAllAnalytics(WebElement element, String value) {
        String[] expectedArray = value.split(",");
        String[] actualArray = getAttributesByPartialName(element, "data").replace("{", "").replace("}", "").split(",");
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

    public String checkBgColor(WebElement webElement, String text) {
        String str = webElement.getCssValue("background-color");
        if (text.equalsIgnoreCase(rgbToHex(str))) {
            return "+ Caption wrapper background color is ok \n";
        } else {
            return failedStepLog("- Expected caption wrapper background color to be " + text + ", but got " + rgbToHex(str) + "\n");
        }
    }

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

    private String rgbToHex(String color) {
        String[] str2 = color.split(",");
        return String.format("#%02X%02X%02X", Integer.valueOf(str2[0].replaceAll("\\D+", ""))
                , Integer.valueOf(str2[1].replaceAll("\\D+", ""))
                , Integer.valueOf(str2[2].replaceAll("\\D+", "")));
    }

    public String checkCaptionWrapperFontColor(WebElement webElement, String text) {
        WebElement captionWrapper = webElement.findElement(captionWrapperBy);
        return checkFontColor(captionWrapper, text);
    }

    public String checkCaptionWrapperText(WebElement webElement, String text) {
        WebElement captionWrapper = webElement.findElement(captionWrapperBy);
        String actualText = captionWrapper.getText();
        if (text.equalsIgnoreCase(actualText)) {
            return "+ Caption wrapper text is ok \n";
        } else {
            return failedStepLog("- Expected caption wrapper text to be " + text + ", but got " + actualText + "\n");
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
