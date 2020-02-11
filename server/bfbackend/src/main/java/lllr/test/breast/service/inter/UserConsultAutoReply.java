package lllr.test.breast.service.inter;

import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;

import java.util.List;

public interface UserConsultAutoReply {
    //客服根据用户消息自动回复消息
    List<AutoAnswerTemplate> AutoReply(String message);
}
