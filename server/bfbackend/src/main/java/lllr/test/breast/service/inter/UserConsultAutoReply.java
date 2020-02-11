package lllr.test.breast.service.inter;

import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.dataObject.consult.WeChatMessageItem;

import java.util.List;
import java.util.Map;

public interface UserConsultAutoReply {
    //客服根据用户消息自动回复消息
    List<AutoAnswerTemplate> AutoReply(String message);
}
