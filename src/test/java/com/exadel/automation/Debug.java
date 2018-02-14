package com.exadel.automation;

import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.TestRunCreator;
import com.rmn.testrail.service.TestRailCommand;
import com.rmn.testrail.service.TestRailService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.rcarz.jiraclient.*;

import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static net.rcarz.jiraclient.Field.*;

public class Debug {
    public static void main(String[] args) throws IOException {
//        URL apiEndpoint = new URL("https://newdemoproject.testrail.io/");
//        TestRailService testRailService = new TestRailService(apiEndpoint, "solo070909@mail.ru", "ZfGfxTL390vNv9Gjb8TK");
//
//        Project project = testRailService.getProjectByName("Demo_project");
//        TestRunCreator testRunCreator = new TestRunCreator();
//        testRunCreator.setName("New");
//
//        testRailService.addTestRun(1,testRunCreator);
//        project.getTestSuites().get(0).getName();
//
//        System.out.println(project.getTestSuites().get(0).getSections().get(0).getName());

        BasicCredentials creds = new BasicCredentials("solo070909@mail.ru", "333666999");
        JiraClient jira = new JiraClient("https://autodemo.atlassian.net", creds);

        try {
            Issue issue = jira.searchIssues("project = \"AD\" AND issuetype in (Bug) AND status != \"DONE\" AND summary ~ \"Broken test\"").issues.get(0);
            System.out.println(issue.getDescription());
            System.out.println(jira.getProjects());
            System.out.println(jira.getProject("AD").getLead());
            Issue newIssue = jira.createIssue("AD", "Bug")
                    .field(SUMMARY, "Broken test")
                    .field(DESCRIPTION, "Bad test")
                    .field(ASSIGNEE, "admin")
                    .execute();
        } catch (JiraException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
