package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.user.Score;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);

}