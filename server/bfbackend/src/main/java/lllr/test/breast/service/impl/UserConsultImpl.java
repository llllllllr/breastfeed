package lllr.test.breast.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.service.inter.UserConsult;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.wx.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserConsultImpl implements UserConsult {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsultImpl.class);


    //和微信官方交互
    @Autowired
    private WXUtil wxUtil;

    /*

    ############### 没有域名 未调试
     */
    @Override
    public Object WXAutoReply(Map questionContent) {
        String question = null;
        if(((String)questionContent.get("MsgType")).equalsIgnoreCase("text"))
            question = (String) questionContent.get("Content");

        LOGGER.debug(" ===WXAutoReplyService 用户提问内容:" + questionContent + "===");
        return "成功接收";
    }


}
