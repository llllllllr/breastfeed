package lllr.test.breast.util.qiniu;


import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.net.URLEncoder;

//七牛云上传资源
@Service
public class QiniuUtil {

    //private String accessKey="L97Gq90WDdzGlqrOwgotLQ5vgfQvuAtUtidD9ANf";


    private String accessKey = "L97Gq90WDdzGlqrOwgotLQ5vgfQvuAtUtidD9ANf";
    private String secretKey = "p7wvihmq4PXCPPlitYzHr4heOeB1PNAxAYuHutuC";
    private String bucket = "wdtc";
    private String path = "http://llllllllr.top/";

    public String upLoeadToken(FileInputStream file, String key,String bucket) {

        //上传地域配置
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        //上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, key);

        System.out.println(upToken);
        try {
            Response response = uploadManager.put(file, key, upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            String encodeFileName = URLEncoder.encode(putRet.key, "utf-8");
            String finalUrl = String.format("%s%s", path, encodeFileName);
            System.out.println(finalUrl);
            return finalUrl;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }


    public String getToken(String bucket){

        //上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //有时候会产生不明原因的无法获取token的现象，重复获取
        long expireSeconds = 600;   //过期时间
        StringMap putPolicy = new StringMap();
        System.out.println(upToken + "-->" + upToken.length());
        while (upToken.length()<3){
            upToken = auth.uploadToken(bucket,null,expireSeconds,putPolicy);
        }

        return upToken;

    }
}
