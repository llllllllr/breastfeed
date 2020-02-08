package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.consult.WeChatMessageItem;

public interface WeChatMessageItemMapper {
    int deleteByExample(WeChatMessageItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WeChatMessageItem record);

    int insertSelective(WeChatMessageItem record);

    WeChatMessageItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeChatMessageItem record);

    int updateByPrimaryKey(WeChatMessageItem record);
}