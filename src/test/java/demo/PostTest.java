package demo;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostTest {
    public void postMethod(){
        RestAssured.baseURI = "http://10.30.140.11:8000";
        RestAssured.basePath = "/clt/clt/comment.msp";
        Response response =
        given()
                .header("Content-Type","application/x-www-form-urlencoded")
                .param("WD_CP_ID","000000")
                .param("WD_VERSION","6.2.2")
                .param("WD_CHANNEL","3")
                .param("WD_UA","")
                .param("WD_UUID","1f2e1fcfa3e0ddb5")
                .param("loginName","18968111180")
                .param("salt","8a7f4421698eca27")
                .param("encrypt","93ee88f919b9acda58096dd13f2a12cd")
                .formParam("objectId","5169969")
                .formParam("objectType","0")
                .formParam("content","good1")
                .formParam("parentId","0")
                .formParam("cityName","浙江省温州市")
                .formParam("userId","2173423")
                .formParam("loginName","1735226227@SINA")
                .formParam("accountType","4")
                .when().log().all().post();
        int responseCode = response.statusCode();
        String responseContent = response.getBody().print();
        System.out.println("返回状态码:" + String.valueOf(responseCode));
        System.out.println(responseContent );
        response.then().body("resultCode", equalTo("1"));
        response.then().body("resultMsg",equalTo("发表成功"));
    }

    public static void main(String args[]){
        PostTest postTest = new PostTest();
        postTest.postMethod();

    }

}
