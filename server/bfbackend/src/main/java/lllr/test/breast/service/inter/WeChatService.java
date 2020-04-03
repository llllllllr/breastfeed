package lllr.test.breast.service.inter;

import lllr.test.breast.dataObject.consult.WeChatMessageItem;
import org.springframework.stereotype.Service;

import java.util.List;

//聊天记录 增查
//一般聊天记录不需要删除和修改
@Service
public interface WeChatService {
    //增
    boolean insertWeChatMsg(WeChatMessageItem record);

//    //根据用户标识查询相关聊天记录
//    List<WeChatMessageItem> selectWeChatMsgByUserId(String userId);

    //查询与特定用户的聊天记录
    List<WeChatMessageItem> selectWeChatMsgByFromUserIdAndToUserIdAndOid(Integer fromUserId,Integer toUserId,String oid);
}
