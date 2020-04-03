package lllr.test.breast.service.impl;

import lllr.test.breast.dao.mapper.WeChatMessageItemMapper;
import lllr.test.breast.dataObject.consult.WeChatMessageItem;
import lllr.test.breast.service.inter.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private WeChatMessageItemMapper weChatMessageItemMapper;

    @Override
    public boolean insertWeChatMsg(WeChatMessageItem record) {
        int successInsNum = weChatMessageItemMapper.insert(record);
        return successInsNum > 0;
    }

//    @Override
//    public List<WeChatMessageItem> selectWeChatMsgByUserId(String userId) {
//        List<WeChatMessageItem> records = new ArrayList<>();
//        records = weChatMessageItemMapper.selectByFromUserId(userId);
//        return records;
//    }

    @Override
    public List<WeChatMessageItem> selectWeChatMsgByFromUserIdAndToUserIdAndOid(Integer fromUserId, Integer toUserId,String oid) {
        List<WeChatMessageItem> records = new ArrayList<>();
        records = weChatMessageItemMapper.selectByFromUserIdAndToUserIdAndOid(fromUserId,toUserId,oid);
        return records;
    }


}
