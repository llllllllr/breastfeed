package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;

public interface UserService {
    ServerResponse<User> userRegister(User user);        //注册

    ServerResponse<User> userSign(String userName, String userPassword);    //登录

    ServerResponse<User> userTokenSign(String user_token);           //token 持续化登录

    ServerResponse<String> getOpenId(Integer userId);

    ServerResponse UserFindPasswordByCreditId(String creditId, String password);
}
