package lllr.test.breast.controller.websocket;

import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.dataObject.consult.WeChatMessageItem;
import lllr.test.breast.service.inter.UserConsultAutoReply;
import lllr.test.breast.service.inter.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @ServerEndpoint 这个注解有什么作用？
 *
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 *
 */

@Controller
@ServerEndpoint(value = "/websocket/{from_user_id}/{to_user_id}/{oid}")
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    //  这里使用静态，让 service 属于类
    private static WeChatService weChatService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setWeChatService(WeChatService weChatService) {
        this.weChatService = weChatService;
    }

    private static UserConsultAutoReply userConsultAutoReply;

    @Autowired
    public void setUserConsultAutoReply(UserConsultAutoReply userConsultAutoReply){
        this.userConsultAutoReply = userConsultAutoReply;
    }

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    //系统客服默认id
    private static String SYSTEM_SERVICE_ID;

    @Value("${system.service.id}")
    public void setUserConsultAutoReply(String SYSTEM_SERVICE_ID){
        this.SYSTEM_SERVICE_ID = SYSTEM_SERVICE_ID;
    }

    /**
     * 标识当前连接客户端的用户名
     */
    private String user_id;

    /**
     * 标识当前订单
     */
    private String oid;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocketController> webSocketSet = new ConcurrentHashMap<>();


    /*
    客户端和服务端建立连接
    返回之前的聊天记录
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam("from_user_id") String from_user_id,@PathParam("to_user_id")String to_user_id,@PathParam("oid")String oid){
        this.session = session;
        this.user_id = from_user_id;
        this.oid = oid;

        //把当前用户会话缓存
        webSocketSet.put(this.user_id,this);
        LOGGER.info("[WebSocket] 连接成功，当前连接人数:" + webSocketSet.size());

        //查询之前消息并返回之前的消息记录
        ReturnBeforeWeChatMessages(from_user_id,to_user_id,oid);

    }

    //查询之前的聊天数据并返回
    private void ReturnBeforeWeChatMessages(String from_user_id,String to_user_id,String oid){
        //查询返回聊天记录
        List<WeChatMessageItem> items = weChatService.selectWeChatMsgByFromUserIdAndToUserIdAndOid(Integer.valueOf(from_user_id),Integer.valueOf(to_user_id),oid);
        LOGGER.info(" === 用户 " + from_user_id + "和用户 " + to_user_id + "的聊天记录：" + items);

        AppointSending(from_user_id,JSONObject.toJSONString(items));
    }


    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.oid);
        LOGGER.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size() + " 当前在线人： " + webSocketSet);
    }

    /*
    前台发送
    消息格式 json
    {
        "fromUserId":"",
        "toUserId":""'
        "messageType":"",
        "messageContent":"",
        "time":""
    }

    后台返回：
    数据无误：
    {"msg":"成功发送!","status":1}

    数据有误：
    {"msg":"系统错误，请稍后再试！","status":0}


     */
    @OnMessage
    public void OnMessage(String message){
        LOGGER.info("[WebSocket] 收到消息：{}",message);

        HandleUserMsg(message);
    }

    //处理用户发送的消息
    /*
    当发送方的user_id为 000000 时，默认为发送给系统客服，系统自动处理后发送给用户

     */
    private void HandleUserMsg(String message){
        //将 消息转化为 消息条目
        WeChatMessageItem weChatMessageItem = JSONObject.parseObject(message,WeChatMessageItem.class);

        String toUserId = weChatMessageItem.getToUserId().toString();
        String fromUserId = weChatMessageItem.getFromUserId().toString();
        //数据有误
        if(weChatMessageItem.getFromUserId() == null || weChatMessageItem.getMessageType() == null || weChatMessageItem.getMessageContent() == null || toUserId == null){
            if(fromUserId != null) {
                LOGGER.debug("=== " + fromUserId + " 发送给 " + toUserId + " 用户消息数据有误 ， 发送失败! ===" );
                AppointSending(fromUserId,JSONObject.toJSONString(ServerResponse.createByErrorMsg("系统错误，请稍后再试！")));              //若发送人不为空，则返回错误消息

            }
            return ;
        }

        //系统自动回复
        if(toUserId.equals(SYSTEM_SERVICE_ID)) {
            //系统根据关键词自动回复
            List<AutoAnswerTemplate> templates = userConsultAutoReply.AutoReply(weChatMessageItem.getMessageContent());
            WeChatMessageItem autoReply = new WeChatMessageItem(Integer.valueOf(SYSTEM_SERVICE_ID),Integer.valueOf(fromUserId),3,JSONObject.toJSONString(templates),new Date(System.currentTimeMillis()));

            AppointSending(fromUserId,JSONObject.toJSONString(autoReply));              //发送消息给指定的用户
            //将聊天记录 存入 数据库
            weChatService.insertWeChatMsg(autoReply);
            return;
        }

        //发送消息给指定的用户
        if(webSocketSet.containsKey(toUserId))
            AppointSending(toUserId, JSONObject.toJSONString(weChatMessageItem));

        //将聊天记录 存入 数据库
        weChatService.insertWeChatMsg(weChatMessageItem);

        if(webSocketSet.containsKey(fromUserId))
            AppointSending(fromUserId,JSONObject.toJSONString(ServerResponse.createBysuccessMsg("成功发送!")));              //返回发送成功消息给发送消息用户
    }

    public String getOid(){return oid;}

    /**
     * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
                LOGGER.debug("===  用户消息成功 消息数据：" + message + " ===");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     * @param name
     * @param message
     */
    public void AppointSending(String name,String message){
        try {
            String Toid = webSocketSet.get(name).getOid();
            if(Toid != null && Toid.equals(this.oid)) {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
                LOGGER.debug("===  用户消息成功 消息数据：" + message + " ===");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
