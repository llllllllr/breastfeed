package lllr.test.breast.service;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;

public interface UserService {
    ServerResponse<User> UserRegister(User user) ;
}
