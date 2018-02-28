package com.hpe.automation.verifyelement.components;

import com.hpe.automation.verifyelement.CheckWebElementBase;
import com.hpe.automation.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static com.sun.webkit.network.URLs.newURL;

/**
 * Created by sboreisha on 2/27/2018.
 */
public class ImageOnly extends CheckWebElementBase {
    private By captionWrapperBy = By.cssSelector(".caption-wrapper");

    public ImageOnly(WebDriver driver) {
        super(driver);
    }

    public String checkImageCanBeLoaded(WebElement element) {
        WebElement webElement = element.findElement(By.cssSelector("img"));
        String Source = webElement.getAttribute("currentSrc");
        try {
            BufferedImage img = ImageIO.read(newURL(Source));
            return "+ Image can be loaded";
        } catch (Exception e) {
            return TestBase.failedStepLog("- Image can not be loaded \n");
        }
    }

    public String checkImageRenditions(WebElement element, String expectedSource) {
        WebElement webElement = element.findElement(By.cssSelector("img"));
        String source = webElement.getAttribute("currentSrc");
        try {
            if (source.contains(expectedSource)) {
                return "+ Image rendition is ok";
            }
            return TestBase.failedStepLog("- Image rendition is not OK. Expected " + expectedSource + " but got " + source + "\n");
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

    public String checkAltText(WebElement webElement, String expectedStyle) {
        WebElement element = webElement.findElement(By.tagName("img"));
        String actualSize = element.getAttribute("title");
        if (expectedStyle.equalsIgnoreCase(actualSize)) {
            return "+ Popup text is ok \n";
        } else {
            return TestBase.failedStepLog("- Expected popup text to be " + expectedStyle + ", but got " + actualSize + "\n");
        }
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
            return TestBase.failedStepLog("- Expected caption wrapper text to be " + text + ", but got " + actualText + "\n");
        }
    }

}
