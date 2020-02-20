package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.AnswerMapper;
import lllr.test.breast.dataObject.question.Answer;
import lllr.test.breast.service.inter.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAnwserServiceImpl implements UserAnswerService {

    @Autowired
    AnswerMapper answerMapper;


    @Override
    public ServerResponse<String> insertAnswers(Answer answer) {
        int rowCount = answerMapper.insert(answer);
        if(rowCount <= 0)
            return ServerResponse.createByErrorMsg("插入答案错误");
        return ServerResponse.createBysuccessMsg("插入答案成功!");
    }
}
