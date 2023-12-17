package utils;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementFetch {

    public WebElement getWebElement(String indentifierType, String identifierValue){
        switch (indentifierType.toLowerCase().trim()) {
            case "xpath":
                return BaseTest.driver.findElement(By.xpath(identifierValue));
            case "classname":
                return BaseTest.driver.findElement(By.className(identifierValue));
            case "cssselector":
                return BaseTest.driver.findElement(By.cssSelector(identifierValue));
            case "id":
                return BaseTest.driver.findElement(By.id(identifierValue));
            case "linkText":
                return BaseTest.driver.findElement(By.linkText(identifierValue));
            case "name":
                return BaseTest.driver.findElement(By.partialLinkText(identifierValue));
            case "tagname":
                return BaseTest.driver.findElement(By.tagName(identifierValue));
            case "partiallinktext":
                return BaseTest.driver.findElement(By.partialLinkText(identifierValue));
            default:
                //Return original object in case accepted type is not found.
                return null;
        }
    }

    public List<WebElement> getWebElements(String indentifierType, String identifierValue){
        switch (indentifierType.toLowerCase().trim()) {
            case "xpath":
                return BaseTest.driver.findElements(By.xpath(identifierValue));
            case "classname":
                return BaseTest.driver.findElements(By.className(identifierValue));
            case "cssselector":
                return BaseTest.driver.findElements(By.cssSelector(identifierValue));
            case "id":
                return BaseTest.driver.findElements(By.id(identifierValue));
            case "linkText":
                return BaseTest.driver.findElements(By.linkText(identifierValue));
            case "name":
                return BaseTest.driver.findElements(By.partialLinkText(identifierValue));
            case "tagname":
                return BaseTest.driver.findElements(By.tagName(identifierValue));
            case "partiallinktext":
                return BaseTest.driver.findElements(By.partialLinkText(identifierValue));
            default:
                //Return original object in case accepted type is not found.
                return null;
        }
    }
}
