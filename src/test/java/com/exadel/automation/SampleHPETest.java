package com.exadel.automation;

import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pages.Page;
import com.exadel.automation.pojo.helper.PageComponentLocationChecker;
import com.exadel.automation.pojo.helper.PageComponentReader;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;

@Listeners({AllureListener.class, JiraListener.class})
public class SampleHPETest extends TestBase {

    private Page homepage;

    @BeforeMethod
    public void initPageObjects() {
        homepage = PageFactory.initElements(driver, Page.class);
    }

    @Feature("HPE")
    @Story("HPE RD")
    @Description("Verify RD")
    @Test(dataProvider = "getData", description = "Checking page RD")
    public void testRDS(File fileName) {
        PageComponentReader pageComponentReader = new PageComponentReader(fileName);
        driver.get("https://www.hpe.com/us/en/regression/components/image-only.html");
        PageComponentLocationChecker locationChecker = new PageComponentLocationChecker(driver);
        String result = locationChecker.checkResponsivePage(pageComponentReader.getTestPageObject());
        Assert.assertFalse(result.contains("- "), "There are mismatches in elements, please refer to steps");

    }

    @DataProvider
    public Object[][] getData() {
        File folder = new File("src/test/resources/specs");
        File[] list = folder.listFiles();

        //Rows - Number of times your test has to be repeated.
        //Columns - Number of parameters in test data.
        Object[][] data = new Object[list.length][1];
        for (int i = 0; i < list.length; i++) {
            data[i][0] = list[i];
        }

        return data;
    }
}
