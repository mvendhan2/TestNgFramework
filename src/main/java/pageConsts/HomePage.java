package pageConsts;

import pageObjects.HomePageElement;
import utils.ElementFetch;

public class HomePage {
    ElementFetch elementFetch = new ElementFetch();
    public void signInButton(){
        elementFetch.getWebElement("xpath",HomePageElement.signInButton).click();
    }
}
