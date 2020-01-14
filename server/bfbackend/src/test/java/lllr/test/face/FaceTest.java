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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FaceTest {
    private OkHttpClient client = new OkHttpClient();
    private final String FACE_API_KEY = "y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes";
    private final String FACE_API_SECRET = "jj_UFc-svreUylZ0A3v3TDmUGlguMEti";
    private final String OUTER_ID = "123456";
    private final String DISPLAY_NAME = "breast_test";


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
        File file = new File("D:\\软件应用\\java IDE\\IntelliJ IDEA 2019.1\\项目\\breastfeed\\breastfeed\\server\\bfbackend\\src\\main\\resources\\UserFaceImage\\ddd.jpg");

        //1.创建对应的MediaType
        final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", "testImage.png", fileBody)
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

    @Test
    public void test5(){
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id","123456")
                .add("face_tokens","a0fca38278961d3fb4a7f33b9626633f")
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
        String a = "{\"time_used\": 136, \"faces\": [{\"face_rectangle\": {\"width\": 81, \"top\": 195, \"left\": 187, \"height\": 81}, \"face_token\": \"994fb01c8dcd4af36fc148ba1ef34788\"}], \"image_id\": \"QpdwCbFHZ+qMB9Iezn/QbQ==\", \"request_id\": \"1579007350,feb5b876-5d91-42b7-a2b3-6d668ac05850\", \"face_num\": 1}";
        Map<String,Object> b = JSON.parseObject(a,Map.class);
        JSONArray d = (JSONArray) b.get("faces");
        System.out.println(d.getJSONObject(0).get("face_token"));
        System.out.println(d);
    }

    //search
    /*
    {"thresholds":{"1e-5":73.975,"1e-4":69.101,"1e-3":62.327},"request_id":"1579011789,d4e601dd-31d8-47b0-be4f-6c0a9fda995b","results":[{"user_id":"","confidence":97.005,"face_token":"a0fca38278961d3fb4a7f33b9626633f"}],"time_used":341}


     */
    @Test
    public void test8(){
        String face_token = "4ab0e9c7ab820cf571888e0a67b949d2";
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id",OUTER_ID)
                .add("face_token",face_token)
                .add("return_result_count","1")
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

        }catch (IOException e){
            e.printStackTrace();
        }

        //返回 请求 得到的 参数
        System.out.println(dataMap);

    }
}
