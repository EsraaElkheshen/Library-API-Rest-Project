package testcases.wishlists;


import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.util.*;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC03_UpdateWishList extends TestBase {
    String name = generateRandomFirstName();
    Response response;

    @Test(priority = 1, description = "Update WishList")
    public void verifyUpdateWishList_P() {
        JSONObject requestBody = new JSONObject();
        JSONArray arr = new JSONArray();
        requestBody.put("name", name);
        requestBody.put("books",arr);
        response = given().auth().basic("admin", "admin").header("Content-Type", "application/json")
                .header("G-Token", "ROM831ESV")
                .body(requestBody.toJSONString())
                .when().put("/wishlists/"+wishListId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.getTime() < 3000);
        Assert.assertEquals(response.jsonPath().getString("name"), name);
        List<Object> actualBooks = response.jsonPath().getList("books");
        Assert.assertTrue(actualBooks.isEmpty(), "Books list should be empty");
        wishListId = response.jsonPath().getInt("id");
        wishListCreatedAt = response.jsonPath().getString("createdAt");
        wishListUpdatedAt = response.jsonPath().getString("updatedAt");
        wishListName = response.jsonPath().getString("name");
        wishListBooks = response.jsonPath().getList("books");
    }
}