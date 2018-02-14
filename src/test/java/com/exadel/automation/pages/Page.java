package com.exadel.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public class Page {
  /*
   * Constructor injecting the WebDriver interface
   * 
   * @param webDriver
   */
  protected WebDriver driver;
  protected Logger logger;

  public Page(WebDriver driver) {
    this.driver = driver;
    this.logger = LoggerFactory.getLogger(this.getClass());
    PageFactory.initElements(driver, this);
  }

  @Step("Navigate to Login page")
  public LoginPage navigateToLoginPage(String url){
    logger.info("Navigate to Login page");
    driver.get(url);
    return new LoginPage(driver);
  }

}
