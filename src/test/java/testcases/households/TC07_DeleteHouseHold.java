package testcases.households;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;

public class TC07_DeleteHouseHold extends TestBase {
    Response response;

    @Test(priority =1 ,description = "delete HouseHold")
    public void verifyDeleteHouseHold_P() {
        System.out.println(userId);
        response = given().auth().basic("admin","admin").header("Content-Type", "application/json")
               .header("G-Token", "ROM831ESV").
                when().delete("/households/"+ houseHoldId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),204);
        Assert.assertTrue(response.getTime()<3000);
    }
}
