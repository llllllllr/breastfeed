package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.question.Test;

public interface TestMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}