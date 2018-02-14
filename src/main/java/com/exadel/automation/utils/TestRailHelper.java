package com.exadel.automation.utils;

import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.entity.TestRunCreator;
import com.rmn.testrail.service.TestRailService;

import java.net.MalformedURLException;
import java.net.URL;

public class TestRailHelper {

    private static PropertiesLoader propertiesLoader = new PropertiesLoader();

    public static TestRailService createTestRailService(String endpoint, String username, String password) throws MalformedURLException {
        URL apiEndpoint = new URL(endpoint);
        return new TestRailService(apiEndpoint, username, password);
    }

    public static TestRun addTestRun(TestRailService testRailService, boolean isIncludeAll) {
        TestRunCreator testRunCreator = new TestRunCreator();
        Project project = testRailService.getProjectByName(propertiesLoader.getTestRailProjectName());
        testRunCreator.setSuiteId(1);
        testRunCreator.setName(project.getName() + " #" + propertiesLoader.getJenkinsBuildNumber());
        testRunCreator.setIncludeAll(isIncludeAll);
        return testRailService.addTestRun(1, testRunCreator);
    }
}
