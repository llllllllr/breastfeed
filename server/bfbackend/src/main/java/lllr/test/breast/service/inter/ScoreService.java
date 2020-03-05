package lllr.test.breast.service.inter;


import lllr.test.breast.common.ServerResponse;

import lllr.test.breast.dataObject.user.Score;
import org.springframework.stereotype.Service;

@Service
public interface ScoreService {


    //添加积分
    ServerResponse<String> addScore(Score score);
    //更新积分
    ServerResponse<String> updateScore(Score score);
    //获取积分
    ServerResponse<Integer> getScore(Integer userid);
}
