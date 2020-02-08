package lllr.test.breast.util.websocket;

import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.WeChatMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.collection.mutable.ArrayBuffer;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @ServerEndpoint 这个注解有什么作用？
 *
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 *
 */

@Component
@ServerEndpoint("/websocket/{user_id}")
public class WebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);
    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String user_id;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String,WebSocket> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "user_id") String name_id){
        this.session = session;
        this.user_id = name_id;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        //webSocketSet.put(name_id,this);
        LOGGER.info("[WebSocket] 连接成功，当前连接人：={}",webSocketSet.toString());
    }


    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.user_id);
        LOGGER.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    /*
    消息格式 json
    {
        "id":"",
        "fromUserId":"",
        "toUserId":""'
        "messageType":"",
        "messageContent":"",
        "time":""
    }
     */
    @OnMessage
    public void OnMessage(String message){
        LOGGER.info("[WebSocket] 收到消息：{}",message);
        JSONObject msg = JSONObject.parseObject(message);
        //将 消息转化为 消息条目
        String flat = (String)msg.get("fromUserId");
        String toUserId = (String)msg.get("toUserId");
        Integer messageType = (Integer)msg.get("messageType");
        String messageContent = (String)msg.get("messageContent");
        Date time = (Date)msg.get("time");

        //数据有误
        if(flat == null || messageType == null || messageContent == null || toUserId == null){
            if(toUserId != null)
               AppointSending(toUserId, ServerResponse.createByErrorMsg("系统错误，请稍后再试！").toString());              //若发送人不为空，则返回错误消息

            return ;
        }

        WeChatMessageItem weChatMessageItem = new WeChatMessageItem(flat,toUserId,messageType,
                messageContent,time);

        webSocketSet.put(toUserId,this);

        //判断是否需要指定发送，具体规则自定义
        AppointSending(user_id,"收到");
        GroupSending(message);

        AppointSending(toUserId, ServerResponse.createBysuccessMsg("成功接收").toString());  //发送成功，返回接收成功消息
    }

    /**
     * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
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
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
