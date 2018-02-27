package com.exadel.automation.verifyelement.video;

import com.exadel.automation.verifyelement.CheckWebElementBase;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by sboreisha on 2/21/2018.
 */
public class CheckVideoYoutube extends CheckWebElementBase implements CheckVideo {
    private By youtubeTimerBy = By.cssSelector(".ytp-time-current");
    private By youtubePlayButton = By.cssSelector(".ytp-play-button");
    private By youTubeMuteButton = By.cssSelector(".ytp-mute-button");
    private By youtubeVolumeSlider = By.cssSelector(".ytp-volume-slider-handle");
    private By youtubeFullscreenButton = By.cssSelector(".ytp-fullscreen-button");
    By closeVideoButton = By.cssSelector(".close-video");
    private By captionTextYoutube = By.cssSelector(".captions-text");
    private By youtubeSubs = By.cssSelector(".ytp-subtitles-button");
    private By sharePopup = By.cssSelector("#at15s");
    private By shareButton = By.cssSelector(".icon-share-global");
    private By playOnYoutubeButton = By.cssSelector(".ytp-youtube-button");

    public CheckVideoYoutube(WebDriver driver) {
        super(driver);
    }

    public void clickPlayVideoButton(WebElement element) {
        pressEsc();
        if (!getElement(videoOverlayBy).getCssValue("display").contains("none")) {
            getElement(closeVideoButton).click();
        }
        element.findElement(By.cssSelector(".img-video-button img")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
    }

    private Boolean checkYoutubeVideoIsPlaying() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(1));
        if (driver.findElements(youtubeTimerBy).size() > 0) {
            logger.info("Youtube timer is shown");
            WebElement timeDisplay = driver.findElement(youtubeTimerBy);
            String timeBefore = timeDisplay.getText();
            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
            Actions actions = new Actions(driver);
            actions.moveToElement(getElement(youTubeMuteButton)).moveByOffset(0, -150).perform();
            if (timeBefore.equals(timeDisplay.getText())) {
                driver.switchTo().defaultContent();
                return false;
            } else {
                driver.switchTo().defaultContent();
                return true;
            }
        }
        driver.switchTo().defaultContent();
        logger.info("Youtube timer is not shown");
        return false;
    }

    @Override
    public String checkVideoAutoplay(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);

        if (checkYoutubeVideoIsPlaying()) {
            pressEsc();
            return "+ Youtube autoplay is ok\n";
        }
        return failedStepLog("- Yuotube video autoplay is not started\n");
    }

    private void clickPlayButton() {
        getElement(youtubePlayButton).click();
    }

    @Override
    public String checkPlayPauseButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        clickPlayButton();
        if (!checkYoutubeVideoIsPlaying()) {
            clickPlayButton();
            if (checkYoutubeVideoIsPlaying()) {
                return "+ youtube play/pause button is working fine\n";
            }
            return failedStepLog("- youtube video is not started after pause\n");
        }
        return failedStepLog("- youtube video is not paused on play button\n");
    }

    @Override
    public String checkSpaceButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        pressSpace();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        if (!checkYoutubeVideoIsPlaying()) {
            pressSpace();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            if (checkYoutubeVideoIsPlaying()) {
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

    private void clickMuteButton() {

        try {
            switchToFrame();
            getElement(youTubeMuteButton).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            logger.info("Mute button is not found");
            //TODO search strategy for mute button
            failedStepLog("- Mute button is not ok\n");
        }

    }

    private void switchToFrame() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(1));
    }

    private Boolean muteIsOn() {
        try {
            switchToFrame();
            logger.info(driver.findElement(youtubeVolumeSlider).getCssValue("left"));
            Boolean result = driver.findElement(youtubeVolumeSlider).getCssValue("left").equalsIgnoreCase("0px");
            driver.switchTo().defaultContent();
            return result;
        } catch (Exception e) {
            failedStepLog(" - Not able to find youtube volume slider\n");
            return false;
        }

    }

    @Override
    public String checkVideoCaption(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        clickSubsButton();
        if (isYoutubeCCPresent()) {
            clickSubsButton();
            if (!isYoutubeCCPresent()) {
                return "+ Subs are shown/hidden on Subs button";
            }
        }
        return failedStepLog("- Video caption for Youtube is not working\n");
    }

    private void clickSubsButton() {
        getElement(youtubeSubs).click();
    }

    private Boolean isYoutubeCCPresent() {
        return checkElementIsPresent(captionTextYoutube);
    }

    @Override
    public String checkFullscreenVideo(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        getElement(youtubeFullscreenButton).click();
        if (isFullscreenShownYoutube()) {
            pressEsc();
            if (!isFullscreenShownYoutube()) {
                return "+ Fullscreen is shown and closed on Esc button\n";
            }
            return failedStepLog("- Fullscreen is not closed on Esc button\n");
        }
        return failedStepLog("- Fullscreen button os not working\n");
    }

    private Boolean isFullscreenShownYoutube() {
        return driver.findElements(By.cssSelector(".ytp-fullscreen")).size() > 0;
    }

    @Override
    public String checkCloseVideoButton(WebElement element) {
        pressEsc();
        clickPlayVideoButton(element);
        getElement(closeVideoButton).click();
        if (driver.findElements(videoOverlayBy).size() > 0) {
            pressEsc();
            return "+ Video is closed on close button\n";
        }
        pressEsc();
        return failedStepLog("- Video is not closed on Close button\n");
    }

    @Override
    public String checkImageClickable(WebElement webElement, String shouldBeShown) {
        return null;
    }

    @Override
    public String checkVideoOverlayStatusOnClick(WebElement webElement, String shouldBeShown) {
        return null;
    }

    @Override
    public String checkShareButton(WebElement element) {
        driver.switchTo().defaultContent();
        pressEsc();
        clickPlayVideoButton(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(shareButton)).pause(350).perform();
        if (getElement(sharePopup).getCssValue("display").equals("block")) {
            pressEsc();
            return "+ Share popup is shown on mouse hover\n";
        }
        pressEsc();
        return failedStepLog("- Share popup is not shown on hover\n");
    }


    public String checkPlayOnYoutube(WebElement element) {
        driver.switchTo().defaultContent();
        pressEsc();
        clickPlayVideoButton(element);
        switchToFrame();
        getElement(playOnYoutubeButton).click();
        driver.switchTo().defaultContent();
        if (youtubeIsOpened()) {
            pressEsc();
            return "+ Youtube site is opened";
        }
        pressEsc();
        return failedStepLog("- Youtube is not opened");
    }

    private Boolean youtubeIsOpened() {
        String winHandleBefore = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        Boolean isOpened = false;
        if (driver.getCurrentUrl().contains("youtube")) {
            isOpened = true;
        }
// Close the new window, if that window no more required
        driver.close();
// Switch back to original browser (first window)
        driver.switchTo().window(winHandleBefore);
        return isOpened;

    }
}
