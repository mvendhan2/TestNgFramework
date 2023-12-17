package testcase;

import Base.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageConsts.HomePage;
import pageConsts.LoginPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class testLoginPage extends BaseTest {
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BaseTest baseTest = new BaseTest();

    @BeforeMethod
    public void setup(){

    }

    @Test(dataProvider = "getData")
    public void LoginFreeCRM(HashMap<String, String> testData) {
        extentTest.info("Clicking the Sign Button");
        homePage.signInButton();
        extentTest.info("Verifying the loginbutton is displayed");
        loginPage.verifyLoginIsDisplayed();
        extentTest.info("Enter the email Id and password");
        String username = testData.get("Username");
        String pass = testData.get("Password");
        loginPage.enterCredentials(testData.get("Username"), testData.get("Password"));
    }
}
