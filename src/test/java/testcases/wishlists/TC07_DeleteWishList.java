package testcases.wishlists;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;

public class TC07_DeleteWishList extends TestBase {
    Response response;

    @Test(priority =1 ,description = "delete WishList")
    public void verifyDeleteWishList_P() {
        System.out.println(userId);
        response = given().auth().preemptive().basic("admin","admin").header("Content-Type", "application/json")
               .header("G-Token", "ROM831ESV").
                when().delete("/wishlists/"+ wishListId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),204);
        Assert.assertTrue(response.getTime()<3000);
    }
}
