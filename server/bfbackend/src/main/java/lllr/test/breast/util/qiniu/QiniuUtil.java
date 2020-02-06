package lllr.test.breast.util.qiniu;


import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import com.qiniu.http.Response;
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
    private String path = "http://pzaqt7zjy.bkt.clouddn.com/";

    public String upLoeadToken(FileInputStream file, String key) {

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
}
