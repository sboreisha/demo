package com.exadel.automation;

import com.exadel.automation.listeners.ApiAllureListener;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.MINOR;
import static io.restassured.RestAssured.get;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@Listeners({ApiAllureListener.class})
public class ApiTest extends TestBase {

    @Feature("API")
    @Story("API JSON")
    @Severity(MINOR)
    @Description("Verify json schema")
    @TmsLink("6")
    @Issue("AD-12")
    @Test(groups = {"api"}, description = "Verify json schema, TC-6")
    public void validateWeatherSchema() {
        get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
                .then().assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Feature("API")
    @Story("API JSON")
    @Severity(MINOR)
    @Description("Verify correct response")
//    @Link(name = "TC-4", value = "4", type = "mylink")
    @TmsLink("7")
    @Issue("AD-12")
    @Test(groups = {"api"}, description = "Verify correct response, TC-7")
    public void validateCorrectWeatherResponse() {
        get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
                .then().statusCode(200).body(
                "name", equalTo("London"),
                "sys.country", equalTo("GB"),
                "visibility", equalTo(10000)
        );
    }

    @Feature("API")
    @Story("API JSON")
    @Severity(MINOR)
    @Description("Verify wrong response")
    @TmsLink("8")
    @Issue("AD-12")
    @Test(groups = {"api"}, description = "Verify wrong response, TC-8")
    public void validateWrongWeatherResponse() {
        get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
                .then().statusCode(200).body(
                "name", equalTo("Minsk"),
                "sys.country", equalTo("Belarus"),
                "visibility", equalTo(0)
        );
    }
}
