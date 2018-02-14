package com.exadel.automation;

import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.utils.PropertiesLoader;
import com.rmn.testrail.entity.TestResult;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.service.TestRailService;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import static com.exadel.automation.WebDriverManager.setupWebDriver;

/**
 * Base class for TestNG-based test classes
 */
@Listeners({AllureListener.class, JiraListener.class})
public class TestBase {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static URL gridHubUrl = null;
    protected static String baseUrl;
    protected static Capabilities capabilities;
    protected static PropertiesLoader propertiesLoader;
    protected static TestRailService testRailService;
    protected static TestRun testRun;
    protected static String testCaseId;
    protected static int testRunId;
    protected static String msg;
    protected static TestResult testResult;
    protected Properties env;
    protected WebDriver driver;

    @Step("{0}")
    public static void stepLog(String message) {
    }

    public static String failedStepLog(String name) {
        Allure.getLifecycle().startStep(Allure.getLifecycle().getCurrentTestCase().toString(),
                new StepResult().withName(name).withStatus(Status.FAILED));
        Allure.getLifecycle().stopStep();
        return name;
    }

    @BeforeSuite(alwaysRun = true)
    public void initTestSuite() throws IOException {
        SuiteConfiguration config = new SuiteConfiguration();
        baseUrl = config.getProperty("site.url");
        capabilities = config.getCapabilities();
        propertiesLoader = new PropertiesLoader();
        setupWebDriver(config);
        // testRailService = createTestRailService(propertiesLoader.getTestRailEndPoint(), propertiesLoader.getTestRailUsername(), propertiesLoader.getTestRailPassword());
        //testRun = addTestRun(testRailService, true);
        //testRunId = testRun.getId();

        env = new Properties();
        env.setProperty("Base URL", baseUrl);
        //env.setProperty("Test rail run", "https://newdemoproject.testrail.io/index.php?/runs/view/" + (testRunId + 1));
    }

    @BeforeMethod
    @Step("Browser initialization")
    public void initWebDriver(Method method) {
        Test test = method.getAnnotation(Test.class);
        if (!StringUtils.containsIgnoreCase(test.groups().toString(), "api")) {
            driver = WebDriverPool.DEFAULT.getDriver(capabilities);
            driver.manage().window().maximize();
        }

        testCaseId = test.description().replaceAll("[^0-9]", "");
        msg = "URL: " + baseUrl;
        testResult = new TestResult();
    }

    @AfterSuite(alwaysRun = true)
    @Step("Browser cleanup")
    public void tearDown() {
        if (driver != null) {
            WebDriverPool.DEFAULT.dismissAll();
        }
        File file = Paths.get(System.getProperty("user.dir"), "/target/allure-results").toAbsolutePath().toFile();
        if (!file.exists()) {
            logger.info("Created dirs: " + file.mkdirs());
        }
        try (FileWriter out = new FileWriter("./target/allure-results/environment.properties")) {
            env.store(out, "Environment variables for report");
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
/*
    @AfterMethod
    @Step("Add Test rail result")
    public void addTestRailResult(ITestResult result) {
      int testsSize = testRailService.getTestRun(testRunId).getTests().size();
        int testId = testRailService.getTestRun(testRunId).getTests().get(Integer.parseInt(testCaseId) - 1).getId();
        //testResult.setTestId(testId);
        if (result.isSuccess()) {
            testResult.setVerdict("Passed");
            testResult.setComment(testResultMessage());
        } else {
            testResult.setVerdict("Failed");
            testResult.setComment(testResultMessage());
            testResult.setDefects(StringUtils.abbreviate(result.getThrowable().toString().replaceAll("java.lang.AssertionError: ", ""), 240));
        }
        testRailService.addTestResult(testId, testResult);
    }*/

    public WebDriver getDriver() {
        return driver;
    }

    private String testResultMessage() {
        return "Env - " + baseUrl
                + "; Jenkins link - http://localhost:8080/job/Demo_java_selenium_tests/" + propertiesLoader.getJenkinsBuildNumber() + "/"
                + "; Allure report - " + allureLink();
    }

    private String allureLink() {
        return "http://localhost:8080/job/Demo_java_selenium_tests/" + propertiesLoader.getJenkinsBuildNumber() + "/allure/";
    }

}
