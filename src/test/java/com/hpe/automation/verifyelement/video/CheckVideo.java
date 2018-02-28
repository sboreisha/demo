package com.hpe.automation.verifyelement.video;

import org.openqa.selenium.WebElement;

/**
 * Created by sboreisha on 2/21/2018.
 */
public interface CheckVideo {
    String checkVideoAutoplay(WebElement element);

    String checkPlayPauseButton(WebElement element);

    String checkSpaceButton(WebElement element);

    String checkMuteUnmute(WebElement element);

    String checkVideoCaption(WebElement element);

    String checkFullscreenVideo(WebElement element);

    String checkCloseVideoButton(WebElement element);

    String checkImageClickable(WebElement webElement, String shouldBeShown);

    String checkVideoOverlayStatusOnClick(WebElement webElement, String shouldBeShown);

    String checkShareButton(WebElement element);
}
