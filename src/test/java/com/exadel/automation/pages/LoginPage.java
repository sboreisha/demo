package com.exadel.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.exadel.automation.utils.ElementsUtil.waitForVisible;

public class LoginPage extends Page {

    private static final String emailSelector = "identifierId";
    private static final String nextButtonSelector = "//span[text() ='Next']";
    private static final String passwordSelector = "password";
    private static final String nonexistentUsernameErrorSelector = "//*[text()=\"Couldn't find your Google Account\"]";
    private static final String incorrectPasswordErrorSelector = "//*[text()='Wrong password. Try again or click Forgot password to reset it.']";
    private static final String forgotEmailButtonSelector = "//*[text()='Forgot email?']";
    private static final String headerSelector = "#headingText";

    private final String loginPageUrl = "http://gmail.com";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = emailSelector)
    private WebElement email;

    @FindBy(xpath = nextButtonSelector)
    private WebElement nextButton;

    @FindBy(name = passwordSelector)
    private WebElement password;

    @FindBy(xpath = nonexistentUsernameErrorSelector)
    private WebElement nonexistentUsernameError;

    @FindBy(xpath = incorrectPasswordErrorSelector)
    private WebElement incorrectPasswordError;

    @FindBy(xpath = forgotEmailButtonSelector)
    private WebElement forgotEmailButton;

    @FindBy(css = headerSelector)
    private WebElement header;

    @Step("Enter email")
    public LoginPage enterEmail(String emailAddress) {
        waitForVisible(driver, By.id(emailSelector));
        logger.info("Enter email: " + emailAddress);
        email.sendKeys(emailAddress);
        return this;
    }

    @Step("Enter Password")
    public LoginPage enterPassword(String pass) {
        waitForVisible(driver, By.name(passwordSelector));
        logger.info("Enter password: " + pass);
        password.sendKeys(pass);
        return this;
    }

    @Step("Click next button")
    public LoginPage clickNextButton() {
        logger.info("Click \"Next\" button");
        nextButton.click();
        return this;
    }

    @Step("Submit Login")
    public InboxPage submitLogin(WebDriver driver) {
        logger.info("Submit login forms");
        nextButton.click();
        return new InboxPage(driver);
    }

    @Step("Click on 'Forgot email' button")
    public LoginPage clickForgotEmail() {
        logger.info("Click on 'Forgot password button'");
        forgotEmailButton.click();
        return this;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public static String getNonexistentUsernameErrorSelector() {
        return nonexistentUsernameErrorSelector;
    }

    public WebElement getNonexistentUsernameError() {
        return nonexistentUsernameError;
    }

    public static String getIncorrectPasswordErrorSelector() {
        return incorrectPasswordErrorSelector;
    }

    public WebElement getIncorrectPasswordError() {
        return incorrectPasswordError;
    }

    public WebElement getHeader() { return header; }
}
