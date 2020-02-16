package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.QuestionMapper;
import lllr.test.breast.dataObject.question.Question;
import lllr.test.breast.service.inter.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionImpl implements QuestionService {
    
    @Autowired
    QuestionMapper questionMapper;
    @Override
    public ServerResponse<List<Question>> getQuestions(Integer tid) {
        List<Question> questions =  questionMapper.getQuestions(tid);
        return ServerResponse.createBysuccessData(questions);
    }
}
