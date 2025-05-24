package testcases.wishlists;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC04_GetWishListById extends TC02_GetWishListById {


    Response response;


    @Test(priority =1 ,description = "get WishList By Id")
    public void verifyGetWishListById_P()  {
        response = given().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV").
                when().get("/wishlists/"+ wishListId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getInt("id"),wishListId);
        Assert.assertEquals(response.jsonPath().getString("createdAt"),wishListCreatedAt);
        Assert.assertEquals(response.jsonPath().getString("updatedAt"),wishListUpdatedAt);
        Assert.assertEquals(response.jsonPath().getString("name"),wishListName);
        Assert.assertEquals(response.jsonPath().getList("books"),wishListBooks);
    }

}
