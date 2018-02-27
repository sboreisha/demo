package com.exadel.automation.verifyelement.components;

import com.exadel.automation.verifyelement.CheckWebElementBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sboreisha on 2/27/2018.
 */
public class TextOnly extends CheckWebElementBase {
    public TextOnly(WebDriver driver) {
        super(driver);
    }


    public String checkTelHyperlink(WebElement element) {
        String result = "";
        try {
            getTelLink(element).click();
            result = "+ Phone link contains phone number\n";
            Boolean isOpened = true;
            if (isAlertPresent()) {
                driver.switchTo().alert();
                driver.switchTo().alert().dismiss();
                driver.switchTo().defaultContent();
                result = "+ Popup has been shown on phone link click\n";
            }
            try {
                String winHandleBefore = driver.getWindowHandle();
                for (String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                }
                driver.close();
                driver.switchTo().window(winHandleBefore);
            } catch (Exception e) {
                logger.info("No new window has been shown on tel link\n");
            }
            if (isOpened) {
                return result;
            }
            return failedStepLog("- URL with phone number is not opened in a new window\n");
        } catch (Exception e) {
            return failedStepLog("- something went wrong with tel on hyperlink\n");
        }
    }

    private WebElement getTelLink(WebElement element) {
        for (WebElement href : element.findElements(By.cssSelector("a"))) {
            if (href.getAttribute("href").contains("tel")) {
                return href;
            }
        }
        return element;
    }

    public String checkPhoneLinkAnalytics(WebElement element, String value) {
        return checkAllAnalytics(getTelLink(element), value);
    }

    public String checkHeaderFontFamily(WebElement element, String family, String header) {
        WebElement textOnlyH2 = element.findElement(By.cssSelector(header));
        return checkFontFamily(textOnlyH2, family);
    }

    public String checkHeaderFontSize(WebElement element, String size, String header) {
        WebElement textOnlyH2 = element.findElement(By.cssSelector(header));
        return checkFontSize(textOnlyH2, size);
    }

    public String checkHeaderLineHeight(WebElement element, String height, String header) {
        WebElement textOnlyH2 = element.findElement(By.cssSelector(header));
        return checkLineHeight(textOnlyH2, height);
    }

    public String checkHeaderFontColor(WebElement element, String color, String header) {
        WebElement textOnlyH2 = element.findElement(By.cssSelector(header));
        return checkFontColor(textOnlyH2, color);
    }

}
