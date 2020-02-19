package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Test;
import lllr.test.breast.service.inter.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestService testService;


    @GetMapping("/test/getTestList")
    @CrossOrigin
    public ServerResponse<List<Test>> getTestList()
    {
        return testService.getTest();
    }
}
