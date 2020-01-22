package lllr.test.breast.service.inter;

public interface UserConsult {
    //微信公众号小程序服务器填写是需要发送验证请求
    boolean WXValidate(String signature,String timestamp,String nonce);

    //客服根据用户消息自动回复消息
    Object WXAutoReply();
}
