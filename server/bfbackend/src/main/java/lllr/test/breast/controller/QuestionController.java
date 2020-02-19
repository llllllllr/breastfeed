package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Answer;
import lllr.test.breast.dataObject.question.Question;
import lllr.test.breast.service.inter.QuestionService;
import lllr.test.breast.service.inter.UserAnswerService;
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
    @Autowired
    UserAnswerService userAnswerService;


    @GetMapping("question/getQustions")
    @CrossOrigin
    ServerResponse<List<Question>> getQuestions(@RequestParam("tid") Integer tid)
    {
        return questionService.getQuestions(tid);
    }

    @GetMapping("user/insertAnswers")
    @CrossOrigin
    ServerResponse<String> insertAnswers(@RequestParam("userid") Integer u_id,@RequestParam("tid") Integer t_id,
                                         @RequestParam("answers")String answers)
    {
        Answer answer = new Answer();
        answer.setAnswers(answers);
        answer.settId(t_id);
        answer.setUserId(u_id);
        return userAnswerService.insertAnswers(answer);
    }
}
