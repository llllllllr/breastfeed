package lllr.test.breast.util.qiniu;

import java.io.Serializable;

public class QiniuResultUtil {

    public static QiniuRes success(String[] data) {
        return new QiniuRes(0, data);
    }

    public static QiniuRes error() {
        return null;
    }

    class UserFaceRes implements Serializable {
    private Integer status;
    private String message;

    public UserFaceRes(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

}
