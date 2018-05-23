package demo;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;

public class GetTest {
    public void getMethod(){
        RestAssured.baseURI = "http://music.163.com";
        RestAssured.basePath = "/album";
        Response response = given()
                .header("Content-Type","text/html;charset=utf8")
                .param("id","38390037")
                .when().log().all().get();
        int responseCode = response.statusCode();
        String responseContent = response.getBody().print();
        System.out.println("返回状态码:" + String.valueOf(responseCode));
        System.out.println(responseContent );
    }
    public static void main(String args[]){
        GetTest getTest = new GetTest();
        getTest.getMethod();

    }
}
