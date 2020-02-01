package lllr.test.breast.service.inter;

import java.util.Map;

public interface UserConsult {
    //客服根据用户消息自动回复消息
    Object WXAutoReply(Map questionContent);
}
