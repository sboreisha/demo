package com.exadel.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPage extends Page {

    private static final String emailSectionSelector = ":2";


    @FindBy(id = emailSectionSelector)
    public WebElement emailSection;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public static String getEmailSectionSelector() {
        return emailSectionSelector;
    }
}
