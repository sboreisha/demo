package com.hpe.automation.verifyelement.components;

import com.hpe.automation.verifyelement.CheckWebElementBase;
import com.hpe.automation.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sboreisha on 2/27/2018.
 */
public class Marquee extends CheckWebElementBase {
    public Marquee(WebDriver driver) {
        super(driver);
    }

    private By ctaButtonVideo = By.cssSelector("div .video-overlay");
    private By ctaButtonLink = By.cssSelector(".cta-link");
    private By mailRadio = By.cssSelector("#yes-permissionemail");
    private By emailForm = By.cssSelector(".contactUsEmailFormComponent");
    private By closePopupForm = By.cssSelector(".btn-close-form");
    private By contactUsLink = By.cssSelector(".contact-lnk-type");
    private By feedbackText = By.cssSelector("#feedbacktext");
    private By backArrow = By.cssSelector(".btn-collapse-form");

    public String checkCTAAllDataAttributesVideo(WebElement element, String value) {
        return checkAllDataAttributes(element.findElement(ctaButtonVideo), value);
    }

    public String checkCTAAllDataAttributesLink(WebElement element, String value) {
        return checkAllDataAttributes(element.findElement(ctaButtonLink), value);
    }

    public String checkCTAGatedLink(WebElement element) {
        Boolean isGatedPageOpen = false;
        try {
            element.findElement(ctaButtonLink).click();
        } catch (Exception e) {
            return "- CTA Button was not found\n";
        }
        String winHandleBefore = driver.getWindowHandle();
        if (driver.getWindowHandles().size() > 1) {

            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            isGatedPageOpen = checkElementIsPresent(mailRadio);
            driver.close();
            driver.switchTo().window(winHandleBefore);
        }
        if (isGatedPageOpen) {
            return "+ Gated resources are opened on external link\n";
        }
        return TestBase.failedStepLog("- Gated resources are not opened on CTA button\n");
    }

    public String checkEmailUsCTA(WebElement element) {
        element.findElement(ctaButtonLink).click();
        if (checkElementIsPresent(emailForm)) {
            closePopUp();
            return "+ Email us popup is shown on link\n";
        }
        return TestBase.failedStepLog("- Email us popup is not shown on link\n");
    }

    public String checkFeedbackFormOnCTA(WebElement element) {
        element.findElement(contactUsLink).click();
        if (checkElementIsPresent(feedbackText)) {
            closePopUp();
            return "+ Feedback form popup is shown on link\n";
        }
        return TestBase.failedStepLog("- Feedback form popup is not shown on link\n");
    }

    private void closePopUp() {
        if (checkElementIsPresent(closePopupForm)) {
            try {
                getElement(closePopupForm).click();
            } catch (Exception e) {
                getElement(backArrow).click();
            }

        } else {
            getElement(backArrow).click();
        }
    }
}
