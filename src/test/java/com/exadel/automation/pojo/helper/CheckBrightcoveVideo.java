package com.exadel.automation.pojo.helper;

import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Listeners;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by sboreisha on 2/21/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class CheckBrightcoveVideo extends CheckWebElementUI implements CheckVideoInterface {
    public CheckBrightcoveVideo(WebDriver driver) {
        super(driver);
    }

    By captionsPopupBy = By.cssSelector(".vjs-subs-caps-button .vjs-menu");
    By captionButtonBy = By.cssSelector(".vjs-subs-caps-button");
    By captionText = By.cssSelector(".vjs-text-track-display div div");
    By closeVideoButton = By.cssSelector(".close-video");

    /**
     * Check if video is opened by video button click and closed by ESC button
     *
     * @param element - container for video
     * @return result of button click
     */
    @Override
    public String checkVideoAutplay(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        Boolean isPlaying = checkBrightCoveVideoIsPlaying();
        if (isPlaying) {
            pressEsc();
            return "+ Brightcove video is opened and auto play started\n";
        }
        pressEsc();
        return failedStepLog("- Video overlay is opened but auto play is not started\n");
    }

    @Override
    public String checkPlayPauseButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        clickPlayPauseBrightcoveButton();
        if (!checkBrightCoveVideoIsPlaying()) {
            clickPlayPauseBrightcoveButton();
            if (checkBrightCoveVideoIsPlaying()) {
                return "+ Play/pause button is working ok\n";
            }
            return failedStepLog("- Video is not started after pause\n");
        }
        return "- Pause button is not working, Play button was not checked";
    }

    @Override
    public String checkSpaceButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        pressSpace();
        if (!checkBrightCoveVideoIsPlaying()) {
            pressSpace();
            if (checkBrightCoveVideoIsPlaying()) {
                return "+ Play/pause on Space button is working ok\n";
            }
            return failedStepLog("- Video is not started after pause on Space\n");
        }
        return "- Pause on Space button is not working, Play button was not checked\n";
    }

    @Override
    public String checkMuteUnmute(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        clickMuteButton();
        if (muteIsOn()) {
            clickMuteButton();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            if (!muteIsOn()) {
                return "+ Mute unmute button works fine\n";
            }
            return failedStepLog("- Unmute is not working\n");
        }
        return failedStepLog("- Mute is not working\n");
    }

    @Override
    public String checkVideoCaption(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        StringBuilder result = new StringBuilder();
        clickOnCaptionButton();
        if (!captionPopupIsShown()) {
            return failedStepLog("- Caption language selection menu is not shown\n");
        }
        clickCaptionItemByIndex(2);
        Uninterruptibles.sleepUninterruptibly(350, TimeUnit.MILLISECONDS);
        if (isCaptionTextDisplayed()) {
            clickOnCaptionButton();
            clickCaptionItemByIndex(1);
        } else {
            return failedStepLog("- Video caption text is not shown on click\n");
        }
        return "+ Video Caption is shown\n";
    }

    @Override
    public String checkFullscreenVideo(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        clickFullscreen();
        if (isFullscreenShownBrightcove()) {
            pressEsc();
            if (!isFullscreenShownBrightcove()) {
                return "+ Fullscreen is shown and closed on Esc button\n";
            }
            return failedStepLog("- Fullscreen is not closed on Esc button\n");
        }
        return failedStepLog("- Fullscreen button os not working\n");
    }

    @Override
    public String checkCloseVideoButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        pressEsc();
        if (driver.findElements(videoOverlayBy).size()>0){
            return "+ Video is closed on close button\n";
        }
        return failedStepLog("- Video is not closed on Close button\n");
    }

    private void clickFullscreen() {
        getElement(By.cssSelector(".vjs-fullscreen-control")).click();
    }

    private Boolean isFullscreenShownBrightcove() {
        return driver.findElements(By.cssSelector(".vjs-fullscreen")).size() > 0;
    }

    private void clickOnCaptionButton() {
        getElement(captionButtonBy).click();
    }

    private Boolean captionPopupIsShown() {
        return getElement(captionsPopupBy).getCssValue("display").equals("block");
    }

    private void clickCaptionItemByIndex(int index) {
        getElement(captionButtonBy).findElements(By.cssSelector("li")).get(index).click();
    }

    private Boolean isCaptionTextDisplayed() {
        return driver.findElements(captionText).size() > 0;
    }

    private Boolean muteIsOn() {
        return driver.findElements(By.cssSelector(".vjs-vol-0")).size() > 0;
    }

    private void clickMuteButton() {
        driver.findElement(By.cssSelector(".vjs-mute-control")).click();
    }

    private void clickPlayPauseBrightcoveButton() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector("#hpe-video-content"))).pause(1500).perform();
        driver.findElement(By.cssSelector(".vjs-play-control")).click();
    }

    private Boolean checkBrightCoveVideoIsPlaying() {
        if (driver.findElements(By.cssSelector(".vjs-remaining-time-display")).size() > 0) {
            WebElement timeDisplay = driver.findElement(By.cssSelector(".vjs-remaining-time-display"));
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

    private void clickPlayVideoButton(WebElement element) {
        pressEsc();
        if (!getElement(videoOverlayBy).getCssValue("display").contains("none")) {
            pressEsc();
        }
        element.findElement(By.cssSelector(".img-video-button img")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
    }

    private void clickCloseVideoButton() {
        getElement(closeVideoButton).click();
    }

    @Override
    public String checkImageClickable(WebElement webElement, String shouldBeShown) {
        // WebElement img = webElement.findElement(By.cssSelector(".img-responsive"));
        return checkVideoOverlayStatusOnClick(webElement, shouldBeShown);
    }

    @Override
    public String checkVideoOverlayStatusOnClick(WebElement webElement, String shouldBeShown) {
        if (checkElementIsPresent(videoOverlayBy)) {
            webElement.click();
            if (getElement(videoOverlayBy).getCssValue("display").contains("none")) {
                if (shouldBeShown.contains("yes")) {
                    pressEsc();
                    return failedStepLog("- Video overlay is not shown on click\n");
                }
                if (shouldBeShown.contains("no")) {
                    pressEsc();
                    return "+ Video overlay is not shown on click\n";
                }
            }
            ;
            if (getElement(videoOverlayBy).getCssValue("display").contains("block")) {
                if (shouldBeShown.contains("yes")) {
                    pressEsc();
                    return "+ Video overlay is shown on click\n";
                }
                if (shouldBeShown.contains("no")) {
                    pressEsc();
                    return failedStepLog("- Video overlay is shown on click\n");
                }
                pressEsc();
            }
        } else {
            if (shouldBeShown.contains("yes")) {
                pressEsc();
                return failedStepLog("- Video overlay is not shown on click\n");
            }
            if (shouldBeShown.contains("no")) {
                pressEsc();
                return "+ Video overlay is not shown on click\n";
            }
        }
        pressEsc();
        return "";
    }



}
