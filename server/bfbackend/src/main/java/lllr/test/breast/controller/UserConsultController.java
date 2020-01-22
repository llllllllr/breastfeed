package lllr.test.breast.controller;

import lllr.test.breast.service.inter.UserConsult;
import lllr.test.breast.util.DataValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/consult")
@Controller
public class UserConsultController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsultController.class);

    @Autowired
    private UserConsult userConsult;


    /*
    在微信公众号小程序平台  填写消息服务器地址后（http：//localhost：8080/consult），
    微信会发一个 GET 请求用于验证:
    将 timestamp nonce 和 WX_TOKEN按字典序排序后用 sha1 算法加密后 与 signature 比较
    相等则说明验证成功 返回 echostr 参数

     */
    @ResponseBody
    @GetMapping("")
    public String WXValidate(@RequestParam(value="signature") String signature, @RequestParam(value="timestamp")String timestamp,
                           @RequestParam(value="nonce") String nonce, @RequestParam(value="echostr")String echostr){

        if(userConsult.WXValidate(signature,timestamp,nonce))
            return echostr;

        return "";
    }


    /*
    接收用户发送给客服的内容，并根据关键词回复
     */
    @ResponseBody
    @PostMapping("/auto")
    public String WXAutoReply(@RequestParam(value="FromUserName") String FromUserName, @RequestParam(value="MsgType") String MsgType,
                              HttpServletRequest request){
        //将请求参数放入map中
        Map<String,String> requestData = new HashMap<>();
        requestData.put("touser",FromUserName);
        requestData.put("MsgType",MsgType);

        //判断用户的消息类型获取对应的参数
        /*消息类型和对应的必需参数
        text-> Content 	文本消息内容
        image-> PicUrl 	图片链接（由系统生成） MediaId 	图片消息媒体id，可以调用[获取临时素材]((getTempMedia)接口拉取数据。
        miniprogrampage-> Title 	标题  AppId 	小程序appid  PagePath 	小程序页面路径
                        ->  ThumbUrl 	封面图片的临时cdn链接   ThumbMediaId 	封面图片的临时素材id
         */


        String access_token = (String) userConsult.WXAutoReply();
        return access_token;
    }

}
