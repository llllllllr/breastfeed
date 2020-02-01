package lllr.test.consult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.util.DataValidateUtil;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConsultTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultTest.class);
    private final String ACCESS_TOKEN = "29_L7QADZHZQ9bendQ22cIMOMTxog7eaF-gcIaDZibQd5eG3GdCH_sCMVVvFdI4ioL1W24Yu8NctD3DngtOTK1GWSzKUYmTyf7gBzqH5JXWVEZhaSbRpU_k34-GSHpyLxQoo_f7V1UXRsAYvNZ5TRKhAEADBU";

    //upload
    @Test
    public void test(){
        String filePath = "D:\\软件应用\\java IDE\\IntelliJ IDEA 2019.1\\项目\\breastfeed\\breastfeed\\server\\bfbackend\\src\\main\\resources\\UserFaceImage\\0.jpeg";
        int index = filePath.lastIndexOf(".");   //计算从后面开始 . 的位置
        String fileType = filePath.substring(index + 1);  //截取图片的类型
        File imageFile = new File(filePath);

        LOGGER.debug("=== WXUploadTempMedia:" + filePath + " ===");

        String url = " https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + ACCESS_TOKEN + "&type=image";

        //1.创建对应的MediaType
        final MediaType MEDIA_TYPE = MediaType.parse("image/" + fileType);

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE, imageFile);

        //3.构建MultipartBody
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file",imageFile.getName() , fileBody);

        RequestBody requestBody = multipartBodyBuilder.build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        //5.发送请求
        //  *** 异常没有处理
        Response response = null;
        OkHttpClient okHttpClient = null;
        Map<String,String> dataMap = null;
        try {
            okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();

            String body = response.body().string();
            //查询得到返回的参数
            dataMap = JSONObject.parseObject(body,Map.class);
            System.out.println(body);
            LOGGER.debug("=== WXUploadTempMedia:" + body + " ===");
            String media_id = dataMap.get("media_id");
            LOGGER.debug("=== WXUploadTempMedia: media_id :" + media_id + " ===");
        } catch (IOException e) {
            LOGGER.error("=== WXUploadTempMedia: " + e.getMessage() + "===");
        }
    }

    String token = "123456";
    String timestamp = "1579607930205";
    String nonce = "111111";

    //access_token
    @Test
    public void test2(){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
        url += "appid=wx3d0c29a20a305f28" + "&secret=685ef10637631ae8e3db77e000f22f9e";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //System.out.println(response.body().string());
            Map<String,String> responseContent = JSON.parseObject(response.body().string(), Map.class);
            System.out.println(responseContent.get("access_token"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void test3(){
        Map<String,Object> map = new HashMap<>();

        System.out.println(map.get("dsfvf"));
    }

    @Test
    public void test4() throws InterruptedException {
        long t = System.currentTimeMillis();
        System.out.println(t);
        Thread.sleep(1000);
        long t1 = System.currentTimeMillis();
        System.out.println(t1);
        System.out.println(t1 - t);

    }

}
