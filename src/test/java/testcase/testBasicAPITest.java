package testcase;

import Base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class testBasicAPITest extends BaseTest {
    String authorizedToken ;
    @Test
    public void postAccountAuthorized(){
        String userName = "TOOLSQA-Test";
        String password = "Test@@123";
        String baseUrl = "https://bookstore.toolsqa.com";
        RestAssured.baseURI = baseUrl;
        Response response = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"userName\": \""+userName+"\",\n" +
                        "  \"password\": \""+password+"\"\n" +
                        "}")
                .post("/Account/v1/GenerateToken");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getBooks(){
        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .get("/BookStore/v1/Books");
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = new JsonPath(response.asString());
       List<Object> val = jsonPath.getList("books.finaAll{it.publisher ==  'O''Reilly Media'}.isbn");
        for(Object result: val){
            System.out.println(result);
        }
    }

}
