package com.exadel.automation.listeners;

import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ApiAllureListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getGroups().length != 0) {
            try {
                jsonSchemaToCheck("schema.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "JSON schema", type = "text/plain")
    public byte[] jsonSchemaToCheck(String jsonFilename) throws IOException {
        //init array with file length
        File myJsonFile = new File(Paths.get("").toAbsolutePath().toString() +
                File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + jsonFilename);
        byte[] bytesArray = new byte[(int) myJsonFile.length()];
        FileInputStream fis = new FileInputStream(myJsonFile);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return bytesArray;
    }
}
