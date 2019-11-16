package lllr.test.breast.service;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;

public interface UserService {
    ServerResponse<User> UserRegister(User user) ;        //注册
    User UserSign(String userName,String userPassword);    //登录
    User UserTokenSign(String user_token);           //token 持续化登录
}
