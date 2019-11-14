package lllr.test.breast.service;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;
import org.springframework.stereotype.Service;

public interface UserService {
    ServerResponse<User> UserRegister(User user);
}
