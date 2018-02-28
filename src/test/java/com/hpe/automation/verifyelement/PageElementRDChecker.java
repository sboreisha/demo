package com.hpe.automation.verifyelement;

import com.hpe.automation.TestBase;
import com.hpe.automation.listeners.AllureListener;
import com.hpe.automation.listeners.JiraListener;
import com.hpe.automation.pojo.Components;
import com.hpe.automation.pojo.Elements;
import com.hpe.automation.pojo.RelativeLocation;
import com.hpe.automation.pojo.TestPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by sboreisha on 2/9/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class PageElementRDChecker extends TestBase {
    protected WebDriver driver;
    protected CheckWebElementBase helper;

    public PageElementRDChecker(WebDriver driver) {
        this.driver = driver;
        helper = new CheckWebElementBase(driver);
    }

    private String isXOk(WebElement element, int x) {
        if (element.getLocation().getX() == x) {
            return "+ X location is ok\n";
        }
        return failedStepLog("- Checking X location failed with " + element.getLocation().getY() + " not equal " + x + "\n", onStepFailure());
    }

    private String isYOk(WebElement element, int x) {
        if (element.getLocation().getY() == x) {
            return "+ Y location is ok\n";
        }
        return failedStepLog("- Checking Y location failed with " + element.getLocation().getY() + " not equal " + x + "\n", onStepFailure());
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
        return failedStepLog("- Checking width failed with " + element.getSize().getWidth() + " not equal " + x + "\n", onStepFailure());
    }

    private String checkResponsiveElement(Elements element, TestPage testPage) {
        int updateJSON = 0;
        CheckWebElementBase helper = new CheckWebElementBase(driver);
        RelativeLocation relativeLocation = element.getRelativeLocation();
        WebElement elementToCheck = helper.getWebelementByPageElement(element);
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
        CheckMethodSelector checker = new CheckMethodSelector(driver);
        StringBuilder result = new StringBuilder();
        if (helper.checkElementIsPresent(element)) {
            result.append(checkResponsiveElement(element, testPage))
                    .append(checker.checkElementLook(element));
            return result.toString();
        }
        return failedStepLog("- Element " + element.getName() + " is not present on the page \n");
    }

    public String checkResponsivePage(TestPage testPage) {
        driver.manage().window().setSize(new Dimension(testPage.getWindowSize().getWidth(), testPage.getWindowSize().getHeight()));
        List<Components> components = testPage.getComponents();
        StringBuilder result = new StringBuilder();
        result.append(testPage.getUrl() + " " + testPage.getWindowSize().toString() + "\n");
        result.append(checkScroll() + "\n");
        for (int i = 0; i < components.size(); i++) {
            result.append("\n            Component " + components.get(i).getName() + "\n");
            for (int j = 0; j < components.get(i).getElements().size(); j++) {
                result.append("   Element "+components.get(i).getElements().get(j).getName()+"\n");
                result.append(doCheckings(components.get(i).getElements().get(j), testPage));
            }
        }

        return result.toString();
    }

    public byte[] onStepFailure() {
        AShot aShot = new AShot();
        aShot.shootingStrategy(ShootingStrategies.viewportPasting(100));
        Screenshot shot = aShot.takeScreenshot(driver);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte;
        try {
            ImageIO.write(shot.getImage(), "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
