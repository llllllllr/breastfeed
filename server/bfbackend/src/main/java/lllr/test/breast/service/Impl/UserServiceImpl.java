package lllr.test.breast.service.Impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.UserMapper;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.dataObject.user.UserExample;
import lllr.test.breast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse<User> UserRegister(User user) {
        UserExample userExample = new UserExample();


        return null;
    }
}
