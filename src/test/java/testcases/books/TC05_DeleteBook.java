package testcases.books;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TC05_DeleteBook extends TestBase {
    Response response;

    @Test(priority =1 ,description = "delete Book")
    public void verifyDeleteBook_P() {
        System.out.println(userId);
        response =
                given().auth().preemptive().basic("admin","admin").header("Content-Type", "application/json").header("G-Token", "ROM831ESV").
                when().delete("/books/"+ bookId).then().assertThat().statusCode(204).extract().response();
        response.then().assertThat().statusCode(204);

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),204);
        Assert.assertTrue(response.getTime()<3000);
    }
}
