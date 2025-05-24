package testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.testng.ITestListener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import java.util.List;


@Listeners({ChainTestListener.class, listeners.Listener.class})
public class TestBase{
    protected SoftAssert softAssert = new SoftAssert();

    //external data
    static int externalBookId;
    public Faker faker = new Faker();
    protected static  String createdAt;
    protected static  String updatedAt;

    //books
    protected static int bookId ;
    protected static String bookCreatedAt;
    protected static String bookUpdatedAt;
    protected static String bookAuthor;
    protected static String bookTitle;
    protected static String bookIsbn;
    protected static String bookReleaseDate;

    //User
    protected static int userId ;
    protected static String userCreatedAt;
    protected static String userUpdatedAt;
    protected static String userFirstName;
    protected static String userLastName;
    protected static String userEmail;

   //houseHold
    protected static int houseHoldId ;
    protected static String houseHoldCreatedAt;
    protected static String houseHoldUpdatedAt;
    protected static String houseHoldName;

    //wishLists
    protected static List<Object> wishListBooks;
    protected static int wishListId;
    protected static String wishListCreatedAt ;
    protected static String wishListUpdatedAt;
    protected static String wishListName;

    @Parameters({ "env" })
    @BeforeTest
    public void setUrl(String env){
        RestAssured.baseURI=env;
    }
}
