package testcases.externaldata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC04_CreateNewBook_ReadExcelSheet_2 extends TestBase {
    String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\data.xlsx";

    String title=  getExcelDataWithDate(0 ,0, "Sheet1") ;
    String author = getExcelDataWithDate(1 ,0, "Sheet1") ;
    String isbn= getExcelDataWithDate(2 ,0, "Sheet1") ;
    String releaseDate= getExcelDataWithDate(3 ,0, "Sheet1") ;

    @Test(priority = 1, description = "validate Create New Book With Reading From Excel Sheet")
    public void validateCreateNewBookWithReadingFromExcelSheet_P() {
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
        Assert.assertEquals(response.jsonPath().getString("author"),author);
        Assert.assertEquals(response.jsonPath().getString("isbn"),isbn);
        Assert.assertEquals(response.jsonPath().getString("releaseDate"),releaseDate);
        createdAt = response.jsonPath().getString("createdAt");
        updatedAt = response.jsonPath().getString("updatedAt");
        System.out.println(createdAt);
        System.out.println(updatedAt);
    }

}
