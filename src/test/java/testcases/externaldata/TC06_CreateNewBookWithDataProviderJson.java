package testcases.externaldata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utility.Utilities.readJsonData;


public class TC06_CreateNewBookWithDataProviderJson extends TestBase {


    @Test(priority = 1, description = "createNewBookWithDataProviderJson", dataProvider = "bookData")
    public void createNewBookWithWithDataProviderJson(Map<String, String> requestData) {

        Response response = given()
                .header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV")
                .body(requestData)
                .when()
                .post("/books")
                .then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertTrue(response.getTime() < 3000);
        Assert.assertEquals(response.jsonPath().getString("title"), requestData.get("title"));
        Assert.assertEquals(response.jsonPath().getString("author"), requestData.get("author"));
        Assert.assertEquals(response.jsonPath().getString("isbn"), requestData.get("isbn"));
        Assert.assertEquals(response.jsonPath().getString("releaseDate"), requestData.get("releaseDate"));
    }

    @DataProvider(name = "bookData")
    public Object[][] useDataProvider() throws Exception {
        return readJsonData();
    }



}
