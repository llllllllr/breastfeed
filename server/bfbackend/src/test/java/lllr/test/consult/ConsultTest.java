package lllr.test.consult;

import com.alibaba.fastjson.JSON;
import lllr.test.breast.util.DataValidateUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class ConsultTest {
    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1a");
        list.add("1c");
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    String token = "123456";
    String timestamp = "1579607930205";
    String nonce = "111111";

    //cf2badb63c10674659c05c15437287a3615af214
    @Test
    public void test1(){
        String a = DataValidateUtil.SHA1(nonce + token + timestamp);
        System.out.println(a);
    }

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

}
