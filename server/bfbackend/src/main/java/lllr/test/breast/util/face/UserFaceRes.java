package lllr.test.breast.util.face;

import java.io.Serializable;

/*
人脸识别 注册 和登录 和前端交互接口
status : 1 表示操作成功 0 表示操作失败
message 表示返回的操作信息
 */
public class UserFaceRes implements Serializable {
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
