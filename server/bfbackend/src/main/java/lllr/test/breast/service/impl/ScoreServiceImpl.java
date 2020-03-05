package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.ScoreMapper;
import lllr.test.breast.dataObject.user.Score;
import lllr.test.breast.service.inter.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;
    @Override
    public ServerResponse<String> addScore(Score score) {
        int rowCount = scoreMapper.insert(score);
        if(rowCount > 0) return ServerResponse.createBysuccessMsg("添加成功");
        return ServerResponse.createByErrorMsg("添加失败，未知错误");
    }

    @Override
    public ServerResponse<String> updateScore(Score score) {
        scoreMapper.updateByPrimaryKey( score);
        return null;
    }

    @Override
    public ServerResponse<Integer> getScore(Integer userid) {
        Score score = scoreMapper.selectByPrimaryKey(userid);
        //如果没有，默认添加0积分
        if(score == null)
        {
            Score newscore =  new Score();
            newscore.setScore(0);
            newscore.setUserId(userid);
            scoreMapper.insert(newscore);
            return ServerResponse.createBysuccessData(0);
        }else{
            return ServerResponse.createByErrorData(score.getScore());
        }

    }
}
