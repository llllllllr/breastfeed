package lllr.test.breast.util.wx;

import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.util.DataValidateUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

//与微信小程序官方api 交互的一些操作
@Service
public class WXUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WXUtil.class);

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //微信验证token参数
    @Value("${wx.TOKEN}")
    private String WX_TOKEN;

    //微信小程序 密匙
    @Value("${wx.APP_ID}")
    private String APP_ID;
    @Value("${wx.APP_SECRET}")
    private String APP_SECRET;

    //小程序的access_token
    //有效时间是7200s
    private static String ACCESS_TOKEN;
    private static long ACCESS_TOKEN_OUTTIME;

    /*
    小程序上传给微信图片的标识符和有效期记录
     */
    private static Map<String,Long> mediaId_to_deadDate = new HashMap<>();   //图片标识符对应有效期
    private static Map<String,String> filePath_to_mediaId = new HashMap<>();  //图片路径对应标识符

    //把媒体文件上传到微信服务器。目前仅支持图片。用于发送客服消息或被动回复用户消息。
    //返回图片标识
    //图片的标识符 有效期 官方规定 为 3天
    private String WXUploadTempMedia(String filePath){
        //先检查是否 缓存 了该图片的标识符 和 标识符 是否 过期
        String temp_media_id = filePath_to_mediaId.get(filePath);

        long THREE_DAY_AGO = System.currentTimeMillis() - 60*60*24*3*1000;  //表示三天前

        if((temp_media_id != null) && (mediaId_to_deadDate.get(temp_media_id).compareTo(THREE_DAY_AGO) == 1))
            return temp_media_id;

        int index = filePath.lastIndexOf(".");   //计算从后面开始 . 的位置
        String fileType = filePath.substring(index + 1);  //截取图片的类型
        File imageFile = new File(filePath);

        LOGGER.debug("=== WXUploadTempMedia:" + filePath + " ===");

        String url = " https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + WXGetAccessToken() + "&type=image";

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
            LOGGER.debug("=== WXUploadTempMedia: responseBody:" + body + " ===");
            String media_id = dataMap.get("media_id");
            LOGGER.debug("=== WXUploadTempMedia: media_id:" + media_id + " ===");

            //存入缓存中
            filePath_to_mediaId.put(filePath,media_id);
            mediaId_to_deadDate.put(media_id,System.currentTimeMillis());

            return media_id;
        } catch (IOException e) {
            LOGGER.error("=== WXUploadTempMedia: " + e.getMessage() + "===");
        }
        return "";
    }


    /*

    ############### 没有域名 未调试
     */

    private Map<String,Object> JsonPost(String url,String json_data){
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, json_data);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Map dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
            String body_string = response.body().string();
            dataMap = JSONObject.parseObject(body_string,Map.class);
            LOGGER.debug(" ===JsonPost: " + url + body_string + " ===");
        }catch (IOException e){
            LOGGER.error(" ===JsonPost: " + url + e.getMessage() + " ===");
            return null;
        }

        //返回 请求 得到的 参数
        return dataMap;

    }

    /*
   在微信公众号小程序平台  填写消息服务器地址后（http：//域名：端口/consult），
   微信会发一个 GET 请求用于验证:
   将 timestamp nonce 和 WX_TOKEN按字典序排序后用 sha1 算法加密后 与 signature 比较
   相等则说明验证成功 返回 echostr 参数

    */
    public boolean WXValidate(String signature,String timestamp,String nonce) {
        //按字典序排序 参数
        List<String> uncode_data = new ArrayList<>();
        uncode_data.add(timestamp);
        uncode_data.add(nonce);
        uncode_data.add(WX_TOKEN);
        Collections.sort(uncode_data);

        LOGGER.debug("===" + uncode_data.toString() + "===");

        String code_data = "";
        for(String temp:uncode_data)
            code_data += temp;

        LOGGER.debug("===" + code_data + "===");

        //sha1 加密
        code_data = DataValidateUtil.SHA1(code_data);

        LOGGER.debug("===" + code_data + "===");

        //验证成功 返回  echostr 参数
        if(signature.equalsIgnoreCase(code_data))
            return true;
        return false;
    }


    /*
   获取小程序全局唯一后台接口调用凭据（access_token）
    */
    private String WXGetAccessToken(){
        //判断 ACCESS_TOKEN 是否为空和是否过期
        if(ACCESS_TOKEN != null && ((System.currentTimeMillis() - ACCESS_TOKEN_OUTTIME) / 1000 < 6000))
            return ACCESS_TOKEN;

        OkHttpClient client = new OkHttpClient();
        //微信获取 access_token api
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
        url += "appid=" + APP_ID + "&secret=" + APP_SECRET;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.body() == null) {
                LOGGER.error("=== WXGetAccessToken: 微信AccessToken为空!" );
                return "";
            }

            Map<String,String> responseContent = JSONObject.parseObject(response.body().string(), Map.class);
            //从响应体中获取access_token
            String access_token = responseContent.get("access_token");

            LOGGER.debug("=== 微信access_token: " + access_token + "===");
            ACCESS_TOKEN = access_token;
            ACCESS_TOKEN_OUTTIME = System.currentTimeMillis();
            return  access_token;

        } catch (IOException e) {
            LOGGER.error("=== WXGetAccessToken：" + e.getMessage() + "===");
        }

        return null;

    }

    /*
    根据关键词自动回复用户发送给客服的消息
    msgType  消息类型 0-》文字消息
                    1-》图片消息
                    2-》小程序卡片
     */
    public boolean WXReplyUserQuestion(Map<String,Object> requestData,int msgType){
        JSONObject request_data = new JSONObject(requestData);
        String request_json = request_data.toJSONString();
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token" + WXGetAccessToken();
        Map<String,Object> response_data = JsonPost(url,request_json);
        return false;
    }


}
