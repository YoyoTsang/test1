package demo;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CommentListTest {

    @Test(dependsOnMethods = {"commentPostMethod"})
    public void commentListTestMethod(){
        baseURI = "http://10.30.140.11:8000";
        basePath = "/clt/publish/clt/resource/portal/v1/commentList.jsp";
        given().
                header("Content-Type","application/x-www-form-urlencoded").
                param("WD_CP_ID","000000").
                param("WD_VERSION","6.2.2").
                param("WD_CHANNEL","3").
                param("WD_UA","").
                param("WD_UUID","1f2e1fcfa3e0ddb5").
                param("loginName","18968111180").
                param("salt","8a7f4421698eca27").
                param("encrypt","f20b2c9fae7861285e08dd6fc71d08db").
                formParam("objectId","5169969").
                formParam("objectType","0").
           when().
                post().
           then().
                assertThat().body(matchesJsonSchemaInClasspath("commentListJsonTest.json")).
                body("resultCode", equalTo("1")).
                body("resultMsg", equalTo("处理成功")).
                //body("commentList", hasSize(11)).
                //body("commentList.id",hasItems("12186025","12186024")).
                body("commentList[0].objectName",not("")).
                body("commentList[0].content", equalTo("温州good3"));

    }

    @Test(groups = {"CommentTest"})
    public void commentPostMethod(){
        baseURI = "http://10.30.140.11:8000";
        basePath = "/clt/clt/comment.msp";
        given().
                header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8").
                queryParam("WD_CP_ID","000000").
                queryParam("WD_VERSION","6.2.2").
                queryParam("WD_CHANNEL","3").
                queryParam("WD_UA","").
                queryParam("WD_UUID","1f2e1fcfa3e0ddb5").
                queryParam("loginName","18968111180").
                queryParam("salt","8a7f4421698eca27").
                queryParam("encrypt","93ee88f919b9acda58096dd13f2a12cd").
                formParam("objectId","5169969").
                formParam("objectType","0").
                formParam("content","温州good3").
                formParam("parentId","0").
                formParam("cityName","浙江省温州市").
                formParam("userId","2173423").
                formParam("loginName","1735226227@SINA").
                formParam("accountType","4").
         when().
                post().
         then().
                body("resultMsg",equalTo("发表成功")).
                body("resultCode",equalTo("1"));
    }


}
