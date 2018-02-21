package com.exadel.automation.pojo.helper;

import com.exadel.automation.listeners.AllureListener;
import com.exadel.automation.listeners.JiraListener;
import com.exadel.automation.pojo.Components;
import com.exadel.automation.pojo.TestPage;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by sboreisha on 2/5/2018.
 */
@Listeners({AllureListener.class, JiraListener.class})
public class PageElementReader {
    TestPage response;

    public PageElementReader(File fileName) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(new FileReader(fileName.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        response = gson.fromJson(obj.toJSONString(), TestPage.class);
    }

    public List<Components> getPageComponents() {
        return response.getComponents();
    }

    public TestPage getTestPageObject() {
        return response;
    }

    public String getURL() {
        return response.getUrl();
    }


}
