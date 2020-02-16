package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Question;
import lllr.test.breast.service.inter.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("question/getQustions")
    @CrossOrigin
    ServerResponse<List<Question>> getQuestions(@RequestParam("tid") Integer tid)
    {
        return questionService.getQuestions(tid);
    }
}
