package lllr.test.breast.service.Impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.UserMapper;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.dataObject.user.UserExample;
import lllr.test.breast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse<User> UserRegister(User user) {
        StringBuilder errorMessage = new StringBuilder();
        //根据用户名查询数据库,根据返回结果判断是否用户名被注册
        UserExample userExample = new UserExample();
        userExample.or().andUserNameEqualTo(user.getUserName());
        List<User> list = userMapper.selectByExample(userExample);
        if(list.size() > 0)
            errorMessage.append("用户名");

        //根据用户名查询数据库,根据返回结果判断是否用户名被注册
        userExample.clear();
        list.clear();
        userExample.or().andCreditIdEqualTo(user.getCreditId());
        list = userMapper.selectByExample(userExample);
        if(list.size() > 0) {
            if (errorMessage.length() > 0)
                errorMessage.append("和");
            errorMessage.append("身份证");
        }

        //如果错误消息长度大于零 返回错误消息
        if (errorMessage.length() > 0)
        {
            errorMessage.append("已被注册!");
            return new ServerResponse<User>(0,errorMessage.toString(),user);
        }

        //插入用户注册信息
        int num = userMapper.insert(user);
        if(num > 0)
            return new ServerResponse<>(1,"注册成功!",user);
        return new ServerResponse<User>(0,"注册失败!",user);
    }
}
