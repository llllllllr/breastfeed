package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.UserMapper;
import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /*
    这里返回的 User 对象的 user_id 字段为空
     */
    @Override
    public ServerResponse<User> userRegister(User user) {
        //查询注册的 用户名 是否已经存在
        User userNameTemp = userMapper.selectByUserName(user.getUserName());
        if(userNameTemp != null)
            return ServerResponse.createByErrorMsg("用户名已被注册！");

        //查询 身份证 是否 已经存在
        User CreditIdTemp = userMapper.selectByCreditId(user.getCreditId());
        if(CreditIdTemp != null)
            return ServerResponse.createByErrorMsg("身份证已被注册！");


        //向数据库中 插入 用户信息
        int insNum = userMapper.insert(user);
        return  insNum == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByErrorMsg("系统错误，请稍后后再试");
    }

    @Override
    public ServerResponse<User> userSign(String userName, String userPassword) {
        //查询注册的 用户名 是否已经存在
        User temp = userMapper.selectByUserName(userName);
        if(temp == null || !temp.getUserPassword().equals(userPassword))
            return ServerResponse.createByErrorMsg("账号或密码错误!");

        return ServerResponse.createBysuccessData(temp);
    }

    @Override
    public ServerResponse<User> userTokenSign(String user_token) {
        User temp = userMapper.selectByToken(user_token);
        if(temp == null || !temp.getUserToken().equals(user_token))
            return ServerResponse.createByError();

        return ServerResponse.createBysuccessData(temp);
    }

    @Override
    public ServerResponse<String> getOpenId(Integer userId) {
        if(userId == null)
            return ServerResponse.createByErrorMsg("userId为空");
        String openId = userMapper.getOpenId(userId);
        return ServerResponse.createBysuccessData(openId);
    }

    @Override
    public ServerResponse UserFindPasswordByCreditId(String creditId, String password) {
        if(userMapper.selectByCreditId(creditId) != null && userMapper.updatePasswordByCreditId(creditId,password) == 1 )
            return ServerResponse.createBysuccess();
        return ServerResponse.createByError();
    }


}
