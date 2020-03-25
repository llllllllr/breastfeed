package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.user.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String userName);

    User selectByToken(String user_token);

    User selectByCreditId(String creditId);

    Integer selectLastUserId();

    String getOpenId(Integer userId);

    int updatePasswordByCreditId(@Param(value = "creditId") String creditId,@Param(value = "password") String password);
}