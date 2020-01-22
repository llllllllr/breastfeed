package lllr.test.breast.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.service.inter.UserConsult;
import lllr.test.breast.util.DataValidateUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserConsultImpl implements UserConsult {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsultImpl.class);

    //微信验证token参数
    @Value("${wx.TOKEN}")
    private String WX_TOKEN;

    //微信小程序 密匙
    @Value("${wx.APP_ID}")
    private String APP_ID;
    @Value("${wx.APP_SECRET}")
    private String APP_SECRET;

    /*
   在微信公众号小程序平台  填写消息服务器地址后（http：//localhost：8080/consult），
   微信会发一个 GET 请求用于验证:
   将 timestamp nonce 和 WX_TOKEN按字典序排序后用 sha1 算法加密后 与 signature 比较
   相等则说明验证成功 返回 echostr 参数

    */
    @Override
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

    @Override
    public Object WXAutoReply() {
        String access_token = WXGetAccessToken();
        return access_token;
    }


    /*
    获取小程序全局唯一后台接口调用凭据（access_token）
     */
    private String WXGetAccessToken(){
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

            Map<String,String> responseContent = JSON.parseObject(response.body().string(), Map.class);
            //从响应体中获取access_token
            String access_token = responseContent.get("access_token");

            LOGGER.debug("=== 微信access_token: " + access_token + "===");
            return  access_token== null ? "" : access_token;

        } catch (IOException e) {
            LOGGER.error("=== WXGetAccessToken：" + e.getMessage() + "===");
        }
        return "";

    }


}
