package lllr.test.breast.util.qiniu;

public class QiniuResultUtil {

    public static QiniuRes success(String[] data){
        return new QiniuRes(0,data);
    }
    public static QiniuRes error(){
        return null;
    }
}
