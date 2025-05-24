package testcases.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.CreateUser;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static utility.Utilities.*;

public class TC03_UpdateUser extends TestBase {
    String firstName = generateRandomFirstName();
    String lastName = generateRandomLastName();
    String email = generateRandomEmail();

    Response response;
    CreateUser newUser = new CreateUser();
    ObjectMapper mapper;

    @Test(priority =1 ,description = "Update User")
    public void verifyUpdateUser_P() throws JsonProcessingException {
        newUser.setFirstName(firstName).setLastName(lastName)
                .setEmail(email);
        mapper = new ObjectMapper();

        response = given().auth().basic("admin","admin").header("Content-Type","application/json")
                .header("G-Token","ROM831ESV").body(mapper.writeValueAsString(newUser))
                .when().put("/users/"+userId).then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.getTime()<3000);
        Assert.assertEquals(response.jsonPath().getString("firstName"),firstName);
        Assert.assertEquals(response.jsonPath().getString("lastName"),lastName);
        Assert.assertEquals(response.jsonPath().getString("email"),email);
        userId = response.jsonPath().getInt("id");
        userCreatedAt = response.jsonPath().getString("createdAt");
        userUpdatedAt =response.jsonPath().getString("updatedAt");
        userFirstName =response.jsonPath().getString("firstName");
        userLastName =response.jsonPath().getString("lastName");
        userEmail =response.jsonPath().getString("email");
    }
}
