package com.exadel.automation.pojo.helper;

import com.exadel.automation.TestBase;
import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.Components;
import com.exadel.automation.pojo.Elements;
import com.exadel.automation.pojo.RelativeLocation;
import com.exadel.automation.pojo.TestPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by sboreisha on 2/9/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class PageComponentLocationChecker extends TestBase {
    protected WebDriver driver;
    protected WebElementHelper helper;

    public PageComponentLocationChecker(WebDriver driver) {
        this.driver = driver;
        helper = new WebElementHelper(driver);
    }

    private String isXOk(WebElement element, int x) {
        if (element.getLocation().getX() == x) {
            return "+ X location is ok\n";
        }
        return failedStepLog("- Checking X location failed with " + element.getLocation().getY() + " not equal " + x + "\n");
    }

    private String isYOk(WebElement element, int x) {
        if (element.getLocation().getY() == x) {
            return "+ Y location is ok\n";
        }
        return failedStepLog("- Checking Y location failed with " + element.getLocation().getY() + " not equal " + x + "\n");
    }

    private String isHeightOk(WebElement element, int x) {
        if (element.getSize().getHeight() == x) {
            return "+ Height is ok\n ";
        }
        return failedStepLog("- Checking height failed with " + element.getSize().getHeight() + " not equal " + x + "\n");
    }

    private String isWidthOk(WebElement element, int x) {
        if (element.getSize().getWidth() == x) {
            return "+ Width is ok\n ";
        }
        return failedStepLog("- Checking width failed with " + element.getSize().getWidth() + " not equal " + x + "\n");
    }

    private String checkResponsiveElement(Elements element, TestPage testPage) {
        int updateJSON = 0;
        RelativeLocation relativeLocation = element.getRelativeLocation();
        WebElementHelper helper = new WebElementHelper(driver);
        WebElement elementToCheck = helper.getChildWebelement(element);
        int x = relativeLocation.getX();
        int y = relativeLocation.getY();
        int height = relativeLocation.getHeight();
        int width = relativeLocation.getWidth();
        StringBuilder result = new StringBuilder();
        if (x != -1) {
            result.append(isXOk(elementToCheck, x));
        } else {
            updateJSON = 1;
            element.getRelativeLocation().setX(elementToCheck.getLocation().getX());
        }
        if (y != -1) {
            result.append(isYOk(elementToCheck, y));
        } else {
            element.getRelativeLocation().setY(elementToCheck.getLocation().getY());
            updateJSON = 1;
        }
        if (height != -1) {
            result.append(isHeightOk(elementToCheck, height));
        } else {
            updateJSON = 1;
            element.getRelativeLocation().setHeight(elementToCheck.getSize().getHeight());
        }
        if (width != -1) {
            result.append(isWidthOk(elementToCheck, width));
        } else {
            updateJSON = 1;
            element.getRelativeLocation().setWidth(elementToCheck.getSize().getWidth());
        }
        if (updateJSON == 1) {
            doUpdateJson(testPage);
            result.append(" JSON has been updated\n");
        }
        return result.toString();
    }

    private String getFileName(String url, String size) {
        return url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")).replace(".", "")
                + "_" + size + ".json";
    }

    private void doUpdateJson(TestPage testPage) {
        // used to pretty print result
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String strJson = gson.toJson(testPage);
        String url = getFileName(testPage.getUrl(), String.valueOf(testPage.getWindowSize().getWidth()));
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/test/resources/specs/" + url);
            writer.write(strJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String checkScroll() {
        String execScript = "return document.documentElement.scrollWidth>document.documentElement.clientWidth;";
        JavascriptExecutor scrollBarPresent = (JavascriptExecutor) driver;
        Boolean test = (Boolean) (scrollBarPresent.executeScript(execScript));
        if (test == true) {
            return failedStepLog("- Horizontal scroll is present\n");
        } else {
            return "+ Scrollbar is not present.\n";
        }
    }

    @Step("Start verifying element {element.name} on {testPage.windowSize.width} width")
    private String doCheckings(Elements element, TestPage testPage) {
        ComponentElementChecker checker = new ComponentElementChecker(driver);
        StringBuilder result = new StringBuilder().append(checkResponsiveElement(element, testPage))
                .append(checker.checkElementLook(element));
        return result.toString();
    }

    public String checkResponsivePage(TestPage testPage) {
        driver.manage().window().setSize(new Dimension(testPage.getWindowSize().getWidth(), testPage.getWindowSize().getHeight()));
        List<Components> components = testPage.getComponents();
        StringBuilder result = new StringBuilder();
        result.append(testPage.getUrl() + " " + testPage.getWindowSize().toString() + "\n");
        result.append(checkScroll() + "\n");
        for (int i = 0; i < components.size(); i++) {
            result.append("\nComponent " + components.get(i).getName() + "\n");
            for (int j = 0; j < components.get(i).getElements().size(); j++) {
                result.append(doCheckings(components.get(i).getElements().get(j), testPage));
            }
        }
        return result.toString();
    }
}
