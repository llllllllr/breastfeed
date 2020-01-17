package lllr.test.face;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.util.exception.ImageException;
import lllr.test.breast.util.face.FaceUtil;
import net.minidev.json.JSONUtil;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FaceTest {
    private OkHttpClient client = new OkHttpClient();
    private final String FACE_API_KEY = "y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes";
    private final String FACE_API_SECRET = "jj_UFc-svreUylZ0A3v3TDmUGlguMEti";
    private final String OUTER_ID = "123456";
    private final String DISPLAY_NAME = "breast_test";
    private final String pangzi = "dd0c567313b8f706aeab27ae2b648502";
    private  final String wo = "e6c60682ed30f04e8b546035c6002f20";

    @Test
    public void test() throws IOException {

        String url = "https://api-cn.faceplusplus.com/facepp/v3/compare?" +
                "api_key=y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes&api_secret=" +
                "jj_UFc-svreUylZ0A3v3TDmUGlguMEti";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }


    @Test
    public void test1() throws IOException {
        final MediaType JSON
                = MediaType.get("application/form-data; charset=utf-8");

        String json = "{api_key:y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes,api_secret:" +
                "jj_UFc-svreUylZ0A3v3TDmUGlguMEti,image_url:" +
                "D:\\aaa\\bbb.jpg}";

        FormBody formBody = new FormBody.Builder()
                .add("api_key","y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes")
                .add("api_secret","jj_UFc-svreUylZ0A3v3TDmUGlguMEti")
                .add("image_url","d:\\aaa\\bbb.jpg")
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/detect")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

    }

    //detect
    @Test
    public void test2() throws IOException {
        File file = new File("D:\\软件应用\\java IDE\\IntelliJ IDEA 2019.1\\项目\\breastfeed\\breastfeed\\server\\bfbackend\\src\\main\\resources\\UserFaceImage\\bbb.jpg");

        //1.创建对应的MediaType
        final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", "testImage.jpg", fileBody)
                .addFormDataPart("api_key","y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes")
                .addFormDataPart("api_secret","jj_UFc-svreUylZ0A3v3TDmUGlguMEti")
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/detect")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        Map<String,String> dataMap = JSON.parseObject(body,Map.class);
        System.out.println(body);
        System.out.println(dataMap);

    }


    @Test
    public void test4(){
        com.alibaba.fastjson.JSONObject dataContent = new JSONObject();
        dataContent.put("api_key","FACE_API_KEY");
        dataContent.put("api_secret","FACE_API_SECRET");
        dataContent.put("outer_id","test");
        System.out.println(dataContent.toString());
        System.out.println(dataContent.toJSONString());
    }

    //add
    @Test
    public void test5(){
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id","123456")
                .add("face_tokens",pangzi)
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface")
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            dataMap = JSON.parseObject(response.body().string());
            System.out.println(dataMap);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //create
    @Test
    public void test6(){
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id",OUTER_ID)
                .add("display_name",DISPLAY_NAME)
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/create")
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
            dataMap = JSON.parseObject(response.body().string());

        }catch (IOException e){
            e.printStackTrace();
        }

        //返回 请求 得到的 参数
        System.out.println(dataMap);
    }

    @Test
    public void test7(){
        int a = Integer.parseInt("1");
        System.out.println(a);
    }

    //search
    /*
    {"thresholds":{"1e-5":73.975,"1e-4":69.101,"1e-3":62.327},"request_id":"1579011789,d4e601dd-31d8-47b0-be4f-6c0a9fda995b","results":[{"user_id":"","confidence":97.005,"face_token":"a0fca38278961d3fb4a7f33b9626633f"}],"time_used":341}
    {"thresholds":{"1e-5":73.975,"1e-4":69.101,"1e-3":62.327},"request_id":"1579052398,b9fa3f4b-c06e-4759-b466-f0cd93dfa39c","results":[{"user_id":"","confidence":97.389,"face_token":"a0fca38278961d3fb4a7f33b9626633f"}],"time_used":426}
    {"thresholds":{"1e-5":73.975,"1e-4":69.101,"1e-3":62.327},"request_id":"1579052424,cdb720bc-2c33-44d1-b0ba-3f37f1155bae","results":[{"user_id":"","confidence":97.389,"face_token":"a0fca38278961d3fb4a7f33b9626633f"},{"user_id":"","confidence":97.389,"face_token":"66092695c5a239e075997ad3f1ab8fef"}],"time_used":310}
    {"thresholds":{"1e-5":73.975,"1e-4":69.101,"1e-3":62.327},"request_id":"1579052496,1028d356-e0f9-4794-bd64-f41198a2b2aa","results":[{"user_id":"","confidence":97.389,"face_token":"a0fca38278961d3fb4a7f33b9626633f"},{"user_id":"","confidence":97.389,"face_token":"66092695c5a239e075997ad3f1ab8fef"}],"time_used":284}


     */
    @Test
    public void test8(){
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id",OUTER_ID)
                .add("face_token",wo)
                .add("return_result_count","3")
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/search")
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
            dataMap = JSON.parseObject(response.body().string());
            JSONObject thresholds = (JSONObject) dataMap.get("thresholds");
            float similar_score = thresholds.getFloat("1e-5");
            System.out.println(similar_score);

        }catch (IOException e){
            e.printStackTrace();
        }

        //返回 请求 得到的 参数
        System.out.println(dataMap);

    }

    //delete

    @Test
    public void test9(){
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id",OUTER_ID)
                .add("check_empty","0")
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/delete")
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
            dataMap = JSON.parseObject(response.body().string());

        }catch (IOException e){
            e.printStackTrace();
        }

        //返回 请求 得到的 参数
        System.out.println(dataMap);
    }


}
