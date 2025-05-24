package testcases.books;


import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrytest.Retry;
import testcases.TestBase;

import java.io.File;
import io.qameta.allure.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static model.CreateBook.createNewBook;
import static org.hamcrest.Matchers.equalTo;
import static utility.Utilities.*;
import static utility.Utilities.getRandomDate;

@Epic("Create New Book")

public class TC01_CreateNewBook extends TestBase {
    String title= generateRandomTitle();
    String author = getRandomAuthor();
    String isbn= getRandomIsbn();
    Date startDate = new Date(90, 0, 1);  // 1990-01-01
    Date endDate = new Date(100, 11, 31); // 2000-12-31
    Date randomDate = getRandomDate(startDate, endDate);
    // Convert to String
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    String releaseDate = date.format(randomDate);
    Response response;


    @Feature("Create Book")
    @Story("new API")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority =1 ,description = "Create New Book", retryAnalyzer = Retry.class)
    public void verifyCreateNewBook_P(){
       Response response = given().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV")
                .body(createNewBook(title,author,isbn,releaseDate))
                .when().post("/books").then().assertThat().statusCode(201)
               .body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\schema\\books\\createbook.json")))
                .body("author",equalTo(author))
                .body("title", equalTo(title)).extract().response();
                response.prettyPrint();

        //  ToDo soft Asset:
         softAssert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("title"),title);


        // ToDo Hard Asset:
        Assert.assertEquals(response.statusCode(),201);
        System.out.println("response code is :"+response.statusCode());
        Assert.assertTrue(response.getTime()<3000);
        System.out.println("response time is :"+response.getTime());
        Assert.assertEquals(response.jsonPath().getString("title"),title);
        System.out.println("Title:"+ response.jsonPath().getString("title"));
        Assert.assertEquals(response.jsonPath().getString("author"),author);
        System.out.println("Author is :"+response.jsonPath().getString("author"));
        Assert.assertEquals(response.jsonPath().getString("isbn"),isbn);
        System.out.println("Isbn is :"+response.jsonPath().getString("isbn"));
        Assert.assertEquals(response.jsonPath().getString("releaseDate"),releaseDate);
        System.out.println("releaseDate is :"+response.jsonPath().getString("releaseDate"));

        bookId= response.jsonPath().getInt("id");
        bookCreatedAt = response.jsonPath().getString("createdAt");
        bookUpdatedAt =response.jsonPath().getString("updatedAt");
        bookTitle =response.jsonPath().getString("title");
        bookIsbn=response.jsonPath().getString("isbn");
        bookAuthor =response.jsonPath().getString("author");
        bookReleaseDate =response.jsonPath().getString("releaseDate");
        //softAssert.assertAll();




    }

}
