package testcases.externaldata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class TC01_CreateNewBook_DataFaker extends TestBase {

     String title= faker.name().fullName();
    String author= faker.name().firstName();
    String isbn= faker.name().lastName();
    Date formattedDate = faker.date().birthday(18, 65);
    // Format the date to readable string
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    String releaseDate = date.format(formattedDate);


    @Test(priority = 1, description = "validate Create New Book With Faker Data")
    public void validateCreateNewBookWithFakerData_P() {
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
        Assert.assertEquals(response.jsonPath().getString("title"),title);//bookId = response.jsonPath().get("id");
        createdAt = response.jsonPath().getString("createdAt");
        updatedAt = response.jsonPath().getString("updatedAt");
        System.out.println(createdAt);
        System.out.println(updatedAt);
    }

}
