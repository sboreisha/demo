package com.hpe.automation;

import com.hpe.automation.listeners.AllureListener;
import com.hpe.automation.listeners.JiraListener;
import com.hpe.automation.verifyelement.JSONReader;
import com.hpe.automation.verifyelement.PageElementRDChecker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

@Listeners({AllureListener.class, JiraListener.class})
public class SampleHPETest extends TestBase {


    @BeforeMethod
    public void initPageObjects() {
    }

    @Feature("HPE")
    @Story("HPE RD")
    @Description("Verify RD")
    @Test(dataProvider = "getData", description = "Checking page RD")
    public void testRDS(File fileName) {
        JSONReader pageComponentReader = new JSONReader(fileName);
        System.out.println("---------------------" + baseUrl + pageComponentReader.getTestPageObject().getUrl());
        driver.get(baseUrl + pageComponentReader.getTestPageObject().getUrl());
        PageElementRDChecker locationChecker = new PageElementRDChecker(driver);
        String result = locationChecker.checkResponsivePage(pageComponentReader.getTestPageObject());
        System.out.println(result);
        Assert.assertFalse(result.contains("- "), "There are mismatches in elements, please refer to steps\n" + result);
    }

    @DataProvider
    public Object[][] getData() {
        File dir = new File("src/test/resources/specs/" + folder);
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        //Rows - Number of times your test has to be repeated.
        //Columns - Number of parameters in test data.
        Object[][] data = new Object[files.size()][1];
        for (int i = 0; i < files.size(); i++) {
            data[i][0] = files.get(i);
        }

        return data;
    }
}
