package lllr.test.breast.controller;

import lllr.test.breast.service.inter.UserConsultAutoReply;
import lllr.test.breast.util.wx.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.util.Map;


@RequestMapping("/user/consult")
@Controller
public class UserConsultController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsultController.class);

    @Autowired
    private UserConsultAutoReply userConsultAutoReply;

    //和微信官方交互
    @Autowired
    private WXUtil wxUtil;

    /*
    在微信公众号小程序平台  填写消息服务器地址后（http：//localhost：8080/consult），
    微信会发一个 GET 请求用于验证:
    将 timestamp nonce 和 WX_TOKEN按字典序排序后用 sha1 算法加密后 与 signature 比较
    相等则说明验证成功 返回 echostr 参数

     */
    @ResponseBody
    @GetMapping("/validate")
    public String WXValidate(@RequestParam(value="signature") String signature, @RequestParam(value="timestamp")String timestamp,
                           @RequestParam(value="nonce") String nonce, @RequestParam(value="echostr")String echostr) {

        if (wxUtil.WXValidate(signature, timestamp, nonce))
            return echostr;

        return "";
    }


    /*
    接收用户发送给客服的内容，并根据关键词回复
     */
    @ResponseBody
    @RequestMapping("/auto")
    public String WXAutoReply(@RequestParam(value = "data",required = false)String data){
        LOGGER.debug("=== " + (data == null ? "消息为空!" : data.toString()) + "===");

        //String reply = (String) userConsult.WXAutoReply(requestData);
        return "success";
    }


//    /*
//    接收用户发送给客服的内容，并根据关键词回复
//     */
//    @ResponseBody
//    @PostMapping("/auto")
//    public String WXAutoReply(@RequestBody Map<String,String> requestData){
//        LOGGER.debug("=== " + requestData.toString() + "===");
//
//        String reply = (String) userConsultAutoReply.WXAutoReply(requestData);
//        return reply;
//    }
//



}
