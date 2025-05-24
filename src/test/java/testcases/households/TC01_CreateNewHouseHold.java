package testcases.households;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC01_CreateNewHouseHold extends TestBase {
    String fullName = generateRandomFirstName()+generateRandomLastName();
    Response response;

    @Test(priority =1 ,description = "Create New HouseHold")
    public void verifyCreateNewHouseHold_P(){
        response = given().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV")
                .body("{ \"name\": \""+fullName+"\"\n" + "}")
                .when().post("/households").then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),201);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("name"),fullName);
        houseHoldId = response.jsonPath().getInt("id");
        houseHoldCreatedAt = response.jsonPath().getString("createdAt");
        houseHoldUpdatedAt =response.jsonPath().getString("updatedAt");
        houseHoldName =response.jsonPath().getString("name");
    }

}
