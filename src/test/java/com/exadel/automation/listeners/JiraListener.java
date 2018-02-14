package com.exadel.automation.listeners;

import com.exadel.automation.utils.JiraApiHelper;
import com.exadel.automation.utils.PropertiesLoader;
import io.qameta.allure.Allure;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Test;

import java.util.List;

import static com.exadel.automation.utils.JiraApiHelper.getJiraClient;
import static net.rcarz.jiraclient.Field.ASSIGNEE;
import static net.rcarz.jiraclient.Field.DESCRIPTION;
import static net.rcarz.jiraclient.Field.SUMMARY;

public class JiraListener extends TestListenerAdapter {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private PropertiesLoader propertiesLoader = new PropertiesLoader();

    @Override
    @Step("Create Jira ticket")
    public void onTestFailure(ITestResult result) {
        /*JiraClient jiraClient = getJiraClient();
        String testDescription = result.getMethod().getDescription();
        String testCaseId = result.getMethod().getDescription().replaceAll("[^0-9]", "");
        try {
            List<Issue> issues = jiraClient.searchIssues("project = \"DA\" AND issuetype in (Bug) AND status != \"RESOLVED\" AND summary ~ \"" + testDescription + "\"").issues;

            if (issues.size() == 0) {
                Issue newIssue = jiraClient.createIssue("DA", "Bug")
                        .field(SUMMARY, "[AUTOTESTS], [" + testDescription + "]")
                        .field(DESCRIPTION, jiraDescriptionBuilder(result.getThrowable().toString(), allureLink(), "https://newdemoproject.testrail.io/index.php?/cases/view/" + testCaseId))
//                        .field(LABELS, "automation")
                        .field(ASSIGNEE, "dzmikhalchuk")
                        .execute();
            }
        } catch (JiraException ex) {
            logger.info(ex.getMessage());
        }*/
    }

    private String jiraDescriptionBuilder(String error, String reportLink, String testCaseLink) {
        return "{panel:title=Error|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#fff}\n" +
                "{noformat}" + error + "{noformat}\n" +
                "{panel}\n" +
                "{panel:title=Jenkins|borderColor=#ccc|titleBGColor=#ecf0f1|bgColor=#fff}\n" +
                "[!http://localhost:8080/buildStatus/icon?job=Demo_java_selenium_tests!|http://localhost:8080/job/Demo_java_selenium_tests/]\n" +
                "{panel}\n" +
                "{panel:title=Report|borderColor=#ccc|titleBGColor=#ecf0f1|bgColor=#fff}\n" +
                "[" + "Link to Allure report|" + reportLink + "]\n" +
                "{panel}\n" +
                "{panel:title=Test case|borderColor=#ccc|titleBGColor=#ecf0f1|bgColor=#fff}\n" +
                "[" + "Link to Test case|" + testCaseLink + "]\n" +
                "{panel}";
    }

    private String allureLink() {
        return "http://localhost:8080/job/Demo_java_selenium_tests/" + propertiesLoader.getJenkinsBuildNumber() + "/allure/";
    }

//    public static void createIssue(final String issue) {
//        Allure.addLinks();
//        Allure.LIFECYCLE.fire(new TestCaseEvent() {
//
//            @Override
//            public void process(TestCaseResult context) {
//                context.getLabels().add(AllureModelUtils.createIssueLabel(issue));
//            }
//        });
//    }
}
