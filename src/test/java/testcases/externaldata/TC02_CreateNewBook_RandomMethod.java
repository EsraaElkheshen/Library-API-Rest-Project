package testcases.externaldata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC02_CreateNewBook_RandomMethod extends TestBase {

    String title= generateRandomTitle();
    String author = getRandomAuthor();
    String isbn= getRandomIsbn();

    Date startDate = new Date(90, 0, 1);  // 1990-01-01
    Date endDate = new Date(100, 11, 31); // 2000-12-31
    Date randomDate = getRandomDate(startDate, endDate);
    // Convert to String
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    String releaseDate = date.format(randomDate);



    @Test(priority = 1, description = "validate Create New Book With Random Date")
    public void validateCreateRandomMethod_P() {
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
        createdAt = response.jsonPath().getString("createdAt");
        updatedAt = response.jsonPath().getString("updatedAt");
        System.out.println(createdAt);
        System.out.println(updatedAt);
    }

}
