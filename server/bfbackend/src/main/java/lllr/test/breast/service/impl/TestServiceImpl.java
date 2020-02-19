package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.TestMapper;
import lllr.test.breast.dataObject.question.Test;
import lllr.test.breast.service.inter.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;


    @Override
    public ServerResponse<List<Test>> getTest() {

        List<Test> testList = testMapper.getTestList();
        return ServerResponse.createBysuccessData(testList);
    }
}
