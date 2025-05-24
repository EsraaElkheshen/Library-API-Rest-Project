package testcases.user;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC08_GetUserById extends TC02_GetUserById {


    Response response;


    @Test(priority =1 ,description = "get User By Id")
    public void verifyGetUserById_P() {

        response = given().auth().basic("admin", "admin").header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV").
                when().get("/users/" + userId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertTrue(response.getTime() < 3000);
        Assert.assertEquals(response.jsonPath().getString("message"), "User not found");
    }
}
