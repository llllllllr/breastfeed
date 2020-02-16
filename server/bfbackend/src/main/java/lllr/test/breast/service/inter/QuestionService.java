package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {


    //获取问卷题目
    ServerResponse<List<Question>>  getQuestions(Integer tid);
}
