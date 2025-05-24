package testcases.households;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC06_GetHouseHoldById extends TC02_GetHouseHoldById {


    Response response;


    @Test(priority =1 ,description = "get HouseHold By Id")
    public void verifyGetHouseHoldById_P(){
        response = given().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV").
                when().get("/households/"+ houseHoldId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("name"),houseHoldName);
        Assert.assertEquals(response.jsonPath().getString("createdAt"),houseHoldCreatedAt);
        Assert.assertEquals(response.jsonPath().getString("updatedAt"),houseHoldUpdatedAt);
        Assert.assertEquals(response.jsonPath().getInt("id"),houseHoldId);
    }

}
