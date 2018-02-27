package com.exadel.automation.verifyelement.video;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by sboreisha on 2/27/2018.
 */
public class MarqueeCTABrightcove extends CheckVideoBrightcove {
    public MarqueeCTABrightcove(WebDriver driver) {
        super(driver);
    }

    private By ctaButton = By.cssSelector("div .video-overlay");

    @Override
    public void clickPlayVideoButton(WebElement element) {
        pressEsc();
        if (!getElement(videoOverlayBy).getCssValue("display").contains("none")) {
            getElement(closeVideoButton).click();
        }
        element.findElement(ctaButton).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
    }
}
