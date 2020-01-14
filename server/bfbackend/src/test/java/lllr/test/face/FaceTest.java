package lllr.test.face;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.util.exception.ImageException;
import lllr.test.breast.util.face.FaceUtil;
import net.minidev.json.JSONUtil;
import okhttp3.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FaceTest {
    private OkHttpClient client = new OkHttpClient();
    private final String key = "y9FpstX_g8NM6Q0DnDfwm9Z3YaU-Jqes";
    private final String secret = "jj_UFc-svreUylZ0A3v3TDmUGlguMEti";



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
        Map<String,Object> dataMap = JSON.parseObject(response.body().string(),Map.class);
        System.out.println(dataMap);

    }

    @Test
    public void test3() throws IOException, ImageException {
        File file = new File("D:\\软件应用\\java IDE\\IntelliJ IDEA 2019.1\\项目\\breastfeed\\breastfeed\\server\\bfbackend\\src\\main\\resources\\UserFaceImage\\ddd.jpg");

        File file1 = new File("D:\\教学资料\\大二下复习资料\\数据结构笔记.docx");
        FaceUtil faceUtil = new FaceUtil();
        faceUtil.DetectFace(file);
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
        final MediaType mediaType
                = MediaType.get("application/form-data; charset=utf-8");

        //将参数格式为 json
        JSONObject dataContent = new JSONObject();
        dataContent.put("api_key",key);
        dataContent.put("api_secret",secret);
        dataContent.put("outer_id","test");
        dataContent.put("face_tokens","66092695c5a239e075997ad3f1ab8fef");

        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",key)
                .add("api_secret",secret)
                .add("outer_id","newfaceset")
                .add("face_tokens","66092695c5a239e075997ad3f1ab8fef")
                .build();

        RequestBody body = RequestBody.create(mediaType,dataContent.toJSONString());
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


}
