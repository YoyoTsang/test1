package demo;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelCommentTest {

    String filePath = "/excelData/"; // 文件路径src/testExampleForJmeterData/GetTestCaseExcel/dubbo
    String fileName = "testcase1"; // 文件名，不包含文件后缀.xls
    String caseName = "testCaseComment"; // sheet名

    @DataProvider(name = "commentData")
    public Object[][] getCommentData() throws IOException {
        utils.ExcelUtils e = new utils.ExcelUtils(filePath, fileName, caseName);
        return e.getExcelData();
    }


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

    @Test(groups = {"CommentTest"}, dataProvider = "commentData")
    public void commentPostMethod(HashMap<String, String> data){
        // Map<String,String> paramMap,为post请求的参数列表
        Map<String, String> paramMap = new HashMap<String, String>(100);
        // Map<String,String> headerMap,为header列表
        Map<String, String> headerMap = new HashMap<String, String>(100);
        // String url为post请求的url
        String url = "";
        // 断言list，元素为String类型
        List<String> responseDataList = new ArrayList<>();

        String caseTestOrNot = data.get("CaseTestOrNot");
        String lastOrNot = data.get("LastOrNot");
        String caseNo = data.get("CaseNo");
        String caseName = data.get("CaseName");
        String pre = data.get("Pre");
        String requestType = data.get("RequestType");
        String requestAddr = data.get("RequestAddr");
        String requestHeader = data.get("RequestHeader");
        String requestPara = data.get("RequestPara");
        String httpStatus = data.get("HttpStatus");
        String eResponseData1 = data.get("EResponseData1");
        String eResponseData2 = data.get("EResponseData2");
        String eResponseData3 = data.get("EResponseData3");
        String aResponseData1 = data.get("AResponseData1");
        String aResponseData2 = data.get("AResponseData2");
        String aResponseData3 = data.get("AResponseData3");
        String memo = data.get("Memo");

        // 配置header
        String[] keyValue = requestHeader.split("=", 0);
        headerMap.put(keyValue[0], keyValue[1]);

        // 配置预期ResponseData
        if (eResponseData1.length() > 0) {
            responseDataList.add(eResponseData1);
        }
        if (eResponseData2.length() > 0) {
            responseDataList.add(eResponseData2);
        }
        if (eResponseData3.length() > 0) {
            responseDataList.add(eResponseData3);
        }

        // 构造paramMap
        String[] requestParaS = requestPara.split("&", 0);
        for (int i = 0; i < requestParaS.length; i++) {
            String[] keyValueH = requestParaS[i].split("=", 0);
            paramMap.put(keyValueH[0], keyValueH[1]);
        }

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
                formParams(paramMap).
         when().
                post().
         then().
                body("resultMsg",equalTo("发表成功")).
                body("resultCode",equalTo("1"));
    }
}
