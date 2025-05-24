package testcases.books;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static model.PartialUpdateBook.partialUpdateBook;
import static utility.Utilities.*;
import static utility.Utilities.getRandomDate;

public class TC04_PartialUpdateBook extends TestBase {
    Response response;
    String title= generateRandomTitle();
    String author = getRandomAuthor();
    String isbn= getRandomIsbn();
    Date startDate = new Date(90, 0, 1);  // 1990-01-01
    Date endDate = new Date(100, 11, 31); // 2000-12-31
    Date randomDate = getRandomDate(startDate, endDate);
    // Convert to String
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    String releaseDate = date.format(randomDate);

    @Test(priority = 1, description = "Partial Update Book")
    public void verifyPartialUpdateBook_P() {
        response = given().auth().preemptive().basic("admin", "admin").header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV")
                .body(partialUpdateBook(title, author, isbn, releaseDate))
                .when().patch("/books/"+bookId).then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\schema\\books\\partial.json"))).extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.getTime() < 3000);
        Assert.assertEquals(response.jsonPath().getString("title"), title);
        Assert.assertEquals(response.jsonPath().getString("author"), author);
        Assert.assertEquals(response.jsonPath().getString("isbn"), isbn);
        Assert.assertEquals(response.jsonPath().getString("releaseDate"), releaseDate);
        bookId= response.jsonPath().getInt("id");
        bookCreatedAt = response.jsonPath().getString("createdAt");
        bookUpdatedAt =response.jsonPath().getString("updatedAt");
        bookTitle =response.jsonPath().getString("title");
        bookIsbn=response.jsonPath().getString("isbn");
        bookAuthor =response.jsonPath().getString("author");
        bookReleaseDate =response.jsonPath().getString("releaseDate");
    }
}
