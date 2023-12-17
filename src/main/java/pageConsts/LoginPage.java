package pageConsts;

import org.testng.Assert;
import pageObjects.LoginPageElement;
import utils.ElementFetch;

public class LoginPage {
    ElementFetch elementFetch = new ElementFetch();
    public void verifyLoginIsDisplayed(){
        Assert.assertTrue(elementFetch.getWebElements("cssselector", LoginPageElement.loginButton).size() > 0, "Element not found");
    }

    public void enterCredentials(String EmailId, String Password){
        elementFetch.getWebElement("cssselector", LoginPageElement.loginPageEmailId).sendKeys(EmailId);
        elementFetch.getWebElement("cssselector", LoginPageElement.loginPagePassword).sendKeys(Password);
    }
}
