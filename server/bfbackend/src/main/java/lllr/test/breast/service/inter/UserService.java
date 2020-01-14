package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;

public interface UserService {
    ServerResponse<User> userRegister(User user);        //注册

    User userSign(String userName, String userPassword);    //登录

    User userTokenSign(String user_token);           //token 持续化登录
}
