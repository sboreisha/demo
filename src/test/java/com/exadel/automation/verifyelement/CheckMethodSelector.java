package com.exadel.automation.verifyelement;


import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.ElementChecking;
import com.exadel.automation.pojo.Elements;
import com.exadel.automation.verifyelement.components.ImageOnly;
import com.exadel.automation.verifyelement.components.Marquee;
import com.exadel.automation.verifyelement.components.TextOnly;
import com.exadel.automation.verifyelement.video.CheckVideoBrightcove;
import com.exadel.automation.verifyelement.video.CheckVideoYoutube;
import com.exadel.automation.verifyelement.video.MarqueeCTABrightcove;
import com.exadel.automation.verifyelement.video.MarqueeCTAYoutube;
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
    CheckWebElementBase webElementHelper;
    CheckVideoBrightcove videoHelperBrightcove;
    CheckVideoYoutube videoHelperYoutube;
    ImageOnly imageOnly;
    TextOnly textOnly;
    MarqueeCTAYoutube marqueeCTAYoutube;
    MarqueeCTABrightcove marqueeCTABrightcove;
    Marquee marquee;

    public CheckMethodSelector(WebDriver driver) {
        this.driver = driver;
        this.webElementHelper = new CheckWebElementBase(driver);
        this.videoHelperBrightcove = new CheckVideoBrightcove(driver);
        this.videoHelperYoutube = new CheckVideoYoutube(driver);
        this.imageOnly = new ImageOnly(driver);
        this.textOnly = new TextOnly(driver);
        this.marqueeCTAYoutube = new MarqueeCTAYoutube(driver);
        this.marqueeCTABrightcove = new MarqueeCTABrightcove(driver);
        this.marquee = new Marquee(driver);
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
                        result.append(imageOnly.checkImageRenditions(elementToCheck, checking.getValue()));
                        break;
                    case "checkImageLoaded":
                        result.append(imageOnly.checkImageCanBeLoaded(elementToCheck));
                        break;
                    case "alttext":
                        result.append(imageOnly.checkAltText(elementToCheck, checking.getValue()));
                        break;
                    case "cursor":
                        result.append(imageOnly.checkImageCursor(elementToCheck, checking.getValue()));
                        break;
                    case "overlayIsShownOnClick":
                        result.append(videoHelperBrightcove.checkImageClickable(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperText":
                        result.append(imageOnly.checkCaptionWrapperText(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperTextSize":
                        result.append(imageOnly.checkCaptionWrapperTextSize(elementToCheck, checking.getValue()));
                        break;
                    case "captionFontFamily":
                        result.append(imageOnly.checkCaptionWrapperFontStyle(elementToCheck, checking.getValue()));
                        break;
                    case "captionFontColor":
                        result.append(imageOnly.checkCaptionWrapperFontColor(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperBgColor":
                        result.append(webElementHelper.checkBgColor(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperTextAlign":
                        result.append(imageOnly.checkCaptionWrapperTextAlign(elementToCheck, checking.getValue()));
                        break;
                    case "checkAttribute":
                        result.append(webElementHelper.checkDataAttribute(elementToCheck, checking.getValue()));
                        break;
                    case "checkAllAnalytics":
                        result.append(webElementHelper.checkAllAnalytics(elementToCheck, checking.getValue()));
                        break;
                    case "captionWrapperCursor":
                        result.append(imageOnly.checkCaptionWrapperCursor(elementToCheck, checking.getValue()));
                        break;
                    //-------------------------------Text only
                    case "checkNewWindowOnHyperlink":
                        result.append(textOnly.checkNewWindowOnHyperlink(elementToCheck));
                        break;
                    case "checkTelHyperlink":
                        result.append(textOnly.checkTelHyperlink(elementToCheck));
                        break;
                    case "checkNoNewWindowOnHyperlink":
                        result.append(textOnly.checkNoNewWindowOnHyperlink(elementToCheck));
                        break;
                    case "checkPhoneAnalytics":
                        result.append(textOnly.checkPhoneLinkAnalytics(elementToCheck, checking.getValue()));
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

                    //------------------------------------------Youtube Section
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
                    //------------------------------------Text only H tags checker
                    case "checkH2FontFamily":
                        result.append(textOnly.checkHeaderFontFamily(elementToCheck, checking.getValue(), "h2"));
                        break;
                    case "checkH2FontSize":
                        result.append(textOnly.checkHeaderFontSize(elementToCheck, checking.getValue(), "h2"));
                        break;
                    case "checkH2FontColor":
                        result.append(textOnly.checkHeaderFontColor(elementToCheck, checking.getValue(), "h2"));
                        break;
                    case "checkH2LineHeight":
                        result.append(textOnly.checkHeaderLineHeight(elementToCheck, checking.getValue(), "h2"));
                        break;

                    case "checkH3FontFamily":
                        result.append(textOnly.checkHeaderFontFamily(elementToCheck, checking.getValue(), "h3"));
                        break;
                    case "checkH3FontSize":
                        result.append(textOnly.checkHeaderFontSize(elementToCheck, checking.getValue(), "h3"));
                        break;

                    case "checkH3FontColor":
                        result.append(textOnly.checkHeaderFontColor(elementToCheck, checking.getValue(), "h3"));
                        break;
                    case "checkH3LineHeight":
                        result.append(textOnly.checkHeaderLineHeight(elementToCheck, checking.getValue(), "h3"));
                        break;

                    case "checkH4FontFamily":
                        result.append(textOnly.checkHeaderFontFamily(elementToCheck, checking.getValue(), "h4"));
                        break;
                    case "checkH4FontSize":
                        result.append(textOnly.checkHeaderFontSize(elementToCheck, checking.getValue(), "h4"));
                        break;
                    case "checkH4FontColor":
                        result.append(textOnly.checkHeaderFontColor(elementToCheck, checking.getValue(), "h4"));
                        break;
                    case "checkH4LineHeight":
                        result.append(textOnly.checkHeaderLineHeight(elementToCheck, checking.getValue(), "h4"));
                        break;

                    case "checkH5FontFamily":
                        result.append(textOnly.checkHeaderFontFamily(elementToCheck, checking.getValue(), "h5"));
                        break;
                    case "checkH5FontSize":
                        result.append(textOnly.checkHeaderFontSize(elementToCheck, checking.getValue(), "h5"));
                        break;
                    case "checkH5FontColor":
                        result.append(textOnly.checkHeaderFontColor(elementToCheck, checking.getValue(), "h5"));
                        break;
                    case "checkH5LineHeight":
                        result.append(textOnly.checkHeaderLineHeight(elementToCheck, checking.getValue(), "h5"));
                        break;

                    case "checkH6FontFamily":
                        result.append(textOnly.checkHeaderFontFamily(elementToCheck, checking.getValue(), "h6"));
                        break;
                    case "checkH6FontSize":
                        result.append(textOnly.checkHeaderFontSize(elementToCheck, checking.getValue(), "h6"));
                        break;
                    case "checkH6FontColor":
                        result.append(textOnly.checkHeaderFontColor(elementToCheck, checking.getValue(), "h6"));
                        break;
                    case "checkH6LineHeight":
                        result.append(textOnly.checkHeaderLineHeight(elementToCheck, checking.getValue(), "h6"));
                        break;
                    //---------Marquee CTA
                    case "checkAllDataAttributesVideo":
                        result.append(marquee.checkCTAAllDataAttributesVideo(elementToCheck, checking.getValue()));
                        break;
                    case "checkAllDataAttributesLink":
                        result.append(marquee.checkCTAAllDataAttributesLink(elementToCheck, checking.getValue()));
                        break;
                    case "checkCTANewWindow":
                        result.append(marquee.checkNewWindowOnHyperlink(elementToCheck));
                        break;
                    case "checkCTAGatedLink":
                        result.append(marquee.checkCTAGatedLink(elementToCheck));
                        break;
                    case "checkEmailUsCTA":
                        result.append(marquee.checkEmailUsCTA(elementToCheck));
                        break;
                    case "checkFeedbackCTA":
                        result.append(marquee.checkFeedbackFormOnCTA(elementToCheck));
                        break;
                    //------------------------- Marquee CTA youtube
                    case "checkAutoplayYTCTA":
                        result.append(marqueeCTAYoutube.checkVideoAutoplay(elementToCheck));
                        break;
                    case "checkPlayPauseButtonYTCTA":
                        result.append(marqueeCTAYoutube.checkPlayPauseButton(elementToCheck));
                        break;
                    case "checkPlayPauseOnSpaceYTCTA":
                        result.append(marqueeCTAYoutube.checkSpaceButton(elementToCheck));
                        break;
                    case "checkMuteUnmuteYTCTA":
                        result.append(marqueeCTAYoutube.checkMuteUnmute(elementToCheck));
                        break;
                    case "checkVideoCaptionYTCTA":
                        result.append(marqueeCTAYoutube.checkVideoCaption(elementToCheck));
                        break;
                    case "checkFullscreenVideoYTCTA":
                        result.append(marqueeCTAYoutube.checkFullscreenVideo(elementToCheck));
                        break;
                    case "checkVideoCloseIconYTCTA":
                        result.append(marqueeCTAYoutube.checkCloseVideoButton(elementToCheck));
                        break;
                    case "checkShareYTCTA":
                        result.append(marqueeCTAYoutube.checkShareButton(elementToCheck));
                        break;
                    case "checkPlayOnYoutubeButtonCTA":
                        result.append(marqueeCTAYoutube.checkPlayOnYoutube(elementToCheck));
                        break;
                    case "checkVideoInfoYoutubeCTA":
                        result.append(marqueeCTAYoutube.checkVideoInfoTimer(elementToCheck));
                        break;
                    //-------------------Marquee CTA Brightcove
                    case "checkPlayDirectVideoBCCTA":
                        result.append(marqueeCTABrightcove.checkVideoAutoplay(elementToCheck));
                        break;
                    case "checkPlayPauseButtonBCCTA":
                        result.append(marqueeCTABrightcove.checkPlayPauseButton(elementToCheck));
                        break;
                    case "checkPlayPauseOnSpaceBCCTA":
                        result.append(marqueeCTABrightcove.checkSpaceButton(elementToCheck));
                        break;
                    case "checkMuteUnmuteBCCTA":
                        result.append(marqueeCTABrightcove.checkMuteUnmute(elementToCheck));
                        break;
                    case "checkVideoCaptionBCCTA":
                        result.append(marqueeCTABrightcove.checkVideoCaption(elementToCheck));
                        break;
                    case "checkFullscreenVideoBCCTA":
                        result.append(marqueeCTABrightcove.checkFullscreenVideo(elementToCheck));
                        break;
                    case "checkVideoCloseIconBCCTA":
                        result.append(marqueeCTABrightcove.checkCloseVideoButton(elementToCheck));
                        break;
                    case "checkShareBCCTA":
                        result.append(marqueeCTABrightcove.checkShareButton(elementToCheck));
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
