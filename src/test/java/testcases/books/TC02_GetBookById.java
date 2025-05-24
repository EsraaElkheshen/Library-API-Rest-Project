package testcases.books;


import com.beust.jcommander.Parameter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TC02_GetBookById extends TestBase {
    Response response;

    @Test(priority =1 ,description = "get Book By Id")
    public void verifyGetBookById_P() {
       System.out.println(System.getProperty("user.dir"));
        response  =given().auth().basic("admin", "admin").header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV").
                when().get("/books/" + bookId).then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\schema\\books\\getbook.json"))).extract().response();
        System.out.println("es"+response.asString());
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.getTime() < 3000);
        Assert.assertEquals(response.jsonPath().getString("title"), bookTitle);
        Assert.assertEquals(response.jsonPath().getString("author"), bookAuthor);
        Assert.assertEquals(response.jsonPath().getString("createdAt"), bookCreatedAt);
        Assert.assertEquals(response.jsonPath().getString("isbn"), bookIsbn);
        Assert.assertEquals(response.jsonPath().getString("updatedAt"), bookUpdatedAt);
        Assert.assertEquals(response.jsonPath().getString("releaseDate"), bookReleaseDate);
        Assert.assertEquals(response.jsonPath().getInt("id"), bookId);
    }

    @Test(priority =2 ,description = "Verify GetBook By Id After Deleting")
    public void verifyGetBookByIdAfterDeleting_P() {
        bookId = response.jsonPath().getInt("id");
        response = (Response) given().log().all().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV").
                when().get("/books/"+ bookId).then().log().all().assertThat().statusCode(404).extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),404);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("message"), "Book not found");
        }

}
