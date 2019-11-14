package lllr.test.breast.service.Impl;

import lllr.test.breast.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserServiceImpl {
    @Autowired
    private UserMapper userMapper;


}
