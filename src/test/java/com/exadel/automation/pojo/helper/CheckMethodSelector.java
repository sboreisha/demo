package com.exadel.automation.pojo.helper;


import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.ElementChecking;
import com.exadel.automation.pojo.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import java.util.List;


/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class CheckMethodSelector extends TestBase {
    WebDriver driver;
    CheckWebElementUI webElementHelper;
    CheckVideo videoHelperBrightcove;
    CheckVideoYoutube videoHelperYoutube;

    public CheckMethodSelector(WebDriver driver) {
        this.driver = driver;
        this.webElementHelper = new CheckWebElementUI(driver);
        this.videoHelperBrightcove = new CheckVideoBrightcove(driver);
        this.videoHelperYoutube = new CheckVideoYoutube(driver);
    }

    public String checkElementLook(Elements element) {
        StringBuilder result = new StringBuilder();


        List<ElementChecking> elementCheckings = element.getElementCheckings();
        for (ElementChecking checking : elementCheckings) {
            if (!checking.getValue().contains("-1")) {
                WebElement elementToCheck = webElementHelper.getWebelementByPageElement(element);
                switch (checking.getName()) {
                    case "fontSize":
                        result.append(webElementHelper.checkFontSize(elementToCheck, checking.getValue()));
                        break;
                    case "padding":
                        result.append("");
                        break;
                    case "anything":
                        result.append("");
                        break;
                    case "rendition":
                        result.append(webElementHelper.checkImageRenditions(elementToCheck, checking.getValue()));
                        break;
                    case "checkImageLoaded":
                        result.append(webElementHelper.checkImageCanBeLoaded(elementToCheck));
                        break;
                    case "alttext":
                        result.append(webElementHelper.checkAltText(elementToCheck, checking.getValue()));
                        break;
                    case "cursor":
                        result.append(webElementHelper.checkImageCursor(elementToCheck, checking.getValue()));
                        break;
                    case "overlayIsShownOnClick":
                        result.append(videoHelperBrightcove.checkImageClickable(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperText":
                        result.append(webElementHelper.checkCaptionWrapperText(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperTextSize":
                        result.append(webElementHelper.checkCaptionWrapperTextSize(elementToCheck, checking.getValue()));
                        break;
                    case "captionFontFamily":
                        result.append(webElementHelper.checkCaptionWrapperFontStyle(elementToCheck, checking.getValue()));
                        break;
                    case "captionFontColor":
                        result.append(webElementHelper.checkCaptionWrapperFontColor(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperBgColor":
                        result.append(webElementHelper.checkBgColor(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperTextAlign":
                        result.append(webElementHelper.checkCaptionWrapperTextAlign(elementToCheck, checking.getValue()));
                        break;
                    case "checkAttribute":
                        result.append(webElementHelper.checkDataAttribute(elementToCheck, checking.getValue()));
                        break;
                    case "checkAllAnalytics":
                        result.append(webElementHelper.checkAllAnalytics(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperCursor":
                        result.append(webElementHelper.checkCaptionWrapperCursor(elementToCheck, checking.getValue()));
                        break;
                    case "checkNewWindowOnHyperlink":
                        result.append(webElementHelper.checkNewWindowOnHyperlink(elementToCheck));
                        break;
                    case "checkTelHyperlink":
                        result.append(webElementHelper.checkTelHyperlink(elementToCheck));
                        break;
                    case "checkNoNewWindowOnHyperlink":
                        result.append(webElementHelper.checkNoNewWindowOnHyperlink(elementToCheck));
                        break;
                    case "checkPhoneAnalytics":
                        result.append(webElementHelper.checkPhoneLinkAnalytics(elementToCheck, checking.getValue()));
                        break;

                    //------------------------------------------Brightcove Section
                    case "checkPlayDirectVideoBC":
                        result.append(videoHelperBrightcove.checkVideoAutoplay(elementToCheck));
                        break;
                    case "checkPlayPauseButtonBC":
                        result.append(videoHelperBrightcove.checkPlayPauseButton(elementToCheck));
                        break;
                    case "checkPlayPauseOnSpaceBC":
                        result.append(videoHelperBrightcove.checkSpaceButton(elementToCheck));
                        break;
                    case "checkMuteUnmuteBC":
                        result.append(videoHelperBrightcove.checkMuteUnmute(elementToCheck));
                        break;
                    case "checkVideoCaptionBC":
                        result.append(videoHelperBrightcove.checkVideoCaption(elementToCheck));
                        break;
                    case "checkFullscreenVideoBC":
                        result.append(videoHelperBrightcove.checkFullscreenVideo(elementToCheck));
                        break;
                    case "checkVideoCloseIconBC":
                        result.append(videoHelperBrightcove.checkCloseVideoButton(elementToCheck));
                        break;
                    case "checkShareBC":
                        result.append(videoHelperBrightcove.checkShareButton(elementToCheck));
                        break;

                    //------------------------------------------Youtube Section\
                    case "checkPlayDirectVideoYT":
                        result.append(videoHelperYoutube.checkVideoAutoplay(elementToCheck));
                        break;
                    case "checkPlayPauseButtonYT":
                        result.append(videoHelperYoutube.checkPlayPauseButton(elementToCheck));
                        break;
                    case "checkPlayPauseOnSpaceYT":
                        result.append(videoHelperYoutube.checkSpaceButton(elementToCheck));
                        break;
                    case "checkMuteUnmuteYT":
                        result.append(videoHelperYoutube.checkMuteUnmute(elementToCheck));
                        break;
                    case "checkVideoCaptionYT":
                        result.append(videoHelperYoutube.checkVideoCaption(elementToCheck));
                        break;
                    case "checkFullscreenVideoYT":
                        result.append(videoHelperYoutube.checkFullscreenVideo(elementToCheck));
                        break;
                    case "checkVideoCloseIconYT":
                        result.append(videoHelperYoutube.checkCloseVideoButton(elementToCheck));
                        break;
                    case "checkShareYT":
                        result.append(videoHelperYoutube.checkShareButton(elementToCheck));
                        break;

                    case "checkPlayOnYoutubeButton":
                        result.append(videoHelperYoutube.checkPlayOnYoutube(elementToCheck));
                        break;
                    case "checkVideoInfo":
                        result.append(webElementHelper.checkVideoInfoTimer(elementToCheck));
                        break;
                    default:
                        result.append("");
                        break;
                }
            }
        }

        return result.toString();
    }


}
