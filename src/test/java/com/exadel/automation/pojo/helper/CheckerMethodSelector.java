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
public class CheckerMethodSelector extends TestBase {
    WebDriver driver;
    CheckWebElementUI webElementHelper;
    CheckVideoInterface videoHelper;

    public CheckerMethodSelector(WebDriver driver) {
        this.driver = driver;
        this.webElementHelper = new CheckWebElementUI(driver);
        this.videoHelper = new CheckBrightcoveVideo(driver);
    }

    public String checkElementLook(Elements element) {
        StringBuilder result = new StringBuilder();

        WebElement elementToCheck = webElementHelper.getWebelementByPageElement(element);
        List<ElementChecking> elementCheckings = element.getElementCheckings();
        for (ElementChecking checking : elementCheckings) {
            if (!checking.getValue().contains("-1")) {
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
                        result.append(videoHelper.checkImageClickable(elementToCheck, checking.getValue()));
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
                    case "checkPlayDirectBritecoveVideo":
                        result.append(videoHelper.checkVideoAutplay(elementToCheck));
                        break;
                    case "checkPlayPauseButton":
                        result.append(videoHelper.checkPlayPauseButton(elementToCheck));
                        break;
                    case "checkPlayPauseOnSpaceBrightcove":
                        result.append(videoHelper.checkSpaceButton(elementToCheck));
                        break;
                    case "checkMuteUnmute":
                        result.append(videoHelper.checkMuteUnmute(elementToCheck));
                        break;
                    case "checkVideoCaption":
                        result.append(videoHelper.checkVideoCaption(elementToCheck));
                        break;
                    case "checkFullscreenVideo":
                        result.append(videoHelper.checkFullscreenVideo(elementToCheck));
                        break;
                    case "checkVideoCloseIconBrightcove":
                        result.append(videoHelper.checkCloseVideoButton(elementToCheck));
                    default:
                        result.append("");
                        break;
                }
            }
        }

        return result.toString();
    }


}
