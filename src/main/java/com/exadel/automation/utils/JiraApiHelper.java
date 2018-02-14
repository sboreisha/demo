package com.exadel.automation.utils;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import org.slf4j.LoggerFactory;

public class JiraApiHelper {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    public static PropertiesLoader propertiesLoader = new PropertiesLoader();

    public static JiraClient getJiraClient() {
        BasicCredentials jiraCreads = new BasicCredentials(propertiesLoader.getJiraUsername(), propertiesLoader.getJiraPassword());
        JiraClient jiraClient = new JiraClient(propertiesLoader.getJiraUrl(), jiraCreads);
        return jiraClient;
    }
}
