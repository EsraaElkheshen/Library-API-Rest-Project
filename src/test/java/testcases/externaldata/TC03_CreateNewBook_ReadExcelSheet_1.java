package testcases.externaldata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC03_CreateNewBook_ReadExcelSheet_1 extends TestBase {
    String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\test-Data.xlsx";

    Map<String, String> requestBodyMap = readExcelAsMap(filePath);

    @Test(priority = 1, description = "validate Create New Book With Reading From Excel Sheet")
    public void validateCreateNewBookWithReadingFromExcelSheet_P() {

        Response response = given()
                .header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV")
                .body(requestBodyMap)
                .when()
                .post("/books")
                .then().extract().response();
         response.prettyPrint();

        Assert.assertEquals(response.statusCode(),201);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("title"),requestBodyMap.get("title"));
        Assert.assertEquals(response.jsonPath().getString("Author"),requestBodyMap.get("Author"));
        Assert.assertEquals(response.jsonPath().getString("isbn"),requestBodyMap.get("isbn"));
        Assert.assertEquals(response.jsonPath().getString("releaseDate"),requestBodyMap.get("releaseDate"));
        createdAt = response.jsonPath().getString("createdAt");
        updatedAt = response.jsonPath().getString("updatedAt");
        System.out.println(createdAt);
        System.out.println(updatedAt);
    }

}
