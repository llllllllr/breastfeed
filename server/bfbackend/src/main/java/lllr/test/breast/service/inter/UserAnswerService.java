package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Answer;
import org.springframework.stereotype.Service;


@Service
public interface UserAnswerService {

    //记录答案
   ServerResponse<String> insertAnswers(Answer answer);
}
