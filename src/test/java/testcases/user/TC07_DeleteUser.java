package testcases.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;

public class TC07_DeleteUser extends TestBase {
    Response response;

    @Test(priority =1 ,description = "delete User")
    public void verifyDeleteUser_P() {
        response = given().auth().preemptive().basic("admin","admin").header("Content-Type", "application/json")
               .header("G-Token", "ROM831ESV").
                when().delete("/users/"+ userId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),204);
        Assert.assertTrue(response.getTime()<3000);
    }
}
