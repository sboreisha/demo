package com.exadel.automation.pojo.helper;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by sboreisha on 2/21/2018.
 */
public class CheckYoutubeVideo extends CheckWebElementUI implements CheckVideoInterface {
    By youtubeTimerBy = By.cssSelector(".ytp-time-current");
    By youtubePlayButton = By.cssSelector(".ytp-play-button");

    public CheckYoutubeVideo(WebDriver driver) {
        super(driver);
    }

    private void clickPlayVideoButton(WebElement element) {
        pressEsc();
        if (!getElement(videoOverlayBy).getCssValue("display").contains("none")) {
            pressEsc();
        }
        element.findElement(By.cssSelector(".img-video-button img")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
    }

    private Boolean checkYoutubeVideoIsPlaying() {
        if (driver.findElements(youtubeTimerBy).size() > 0) {
            WebElement timeDisplay = driver.findElement(youtubeTimerBy);
            String timeBefore = timeDisplay.getText();
            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
            if (timeBefore.equals(timeDisplay.getText())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String checkVideoAutplay(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        if (checkYoutubeVideoIsPlaying()) {
            pressEsc();
            return "+ Youtube autoplay is ok";
        }
        return failedStepLog("- Yuotube video autoplay is not started");
    }

    private void clickPlayButton() {
        getElement(youtubePlayButton).click();
    }

    @Override
    public String checkPlayPauseButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);

        return null;
    }

    @Override
    public String checkSpaceButton(WebElement element) {
        return null;
    }

    @Override
    public String checkMuteUnmute(WebElement element) {
        return null;
    }

    @Override
    public String checkVideoCaption(WebElement element) {
        return null;
    }

    @Override
    public String checkFullscreenVideo(WebElement element) {
        return null;
    }

    @Override
    public String checkCloseVideoButton(WebElement element) {
        return null;
    }

    @Override
    public String checkImageClickable(WebElement webElement, String shouldBeShown) {
        return null;
    }

    @Override
    public String checkVideoOverlayStatusOnClick(WebElement webElement, String shouldBeShown) {
        return null;
    }
}
