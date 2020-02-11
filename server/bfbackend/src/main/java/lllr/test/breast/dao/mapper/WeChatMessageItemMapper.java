package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.consult.WeChatMessageItem;

import java.util.List;

public interface WeChatMessageItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WeChatMessageItem record);

    int insertSelective(WeChatMessageItem record);

    WeChatMessageItem selectByPrimaryKey(Integer id);

    List<WeChatMessageItem> selectByFromUserId(String fromUserId);

    int updateByPrimaryKeySelective(WeChatMessageItem record);

    int updateByPrimaryKey(WeChatMessageItem record);

//    List<WeChatMessageItem> selectByFromUserIdAndToUserId(@Param("from") String fromUserId, @Param("to") String toUserId);

    List<WeChatMessageItem> selectByFromUserIdAndToUserId(String fromUserId, String toUserId);
}