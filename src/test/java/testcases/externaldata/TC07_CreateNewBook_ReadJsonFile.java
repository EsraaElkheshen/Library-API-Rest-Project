package testcases.externaldata;

import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC07_CreateNewBook_ReadJsonFile extends TestBase {
    String jsonFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\testData.json";

    String title= getSingleJsonData(jsonFilePath, "title");
    String author = getSingleJsonData(jsonFilePath, "author");
    String isbn = getSingleJsonData(jsonFilePath, "isbn");
    String releaseDate = getSingleJsonData(jsonFilePath, "releaseDate");

    public TC07_CreateNewBook_ReadJsonFile() throws IOException, ParseException {
    }


    @Test(priority = 1, description = "validate Create New Book With Reading From Json File")
    public void validateCreateNewBookWithReadFromJsonFile_P() {
        String requestBody = "{\n" +
                "  \"title\": \""+title+"\",\n" +
                "  \"author\": \""+author+"\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"releaseDate\": \""+releaseDate+"\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV")
                .body(requestBody)
                .when()
                .post("/books")
                .then().extract().response();
         response.prettyPrint();

        Assert.assertEquals(response.statusCode(),201);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("title"),title);
        //bookId = response.jsonPath().get("id");
        createdAt = response.jsonPath().getString("createdAt");
        updatedAt = response.jsonPath().getString("updatedAt");
        //System.out.println(bookId);
        System.out.println(createdAt);
        System.out.println(updatedAt);
    }

}
