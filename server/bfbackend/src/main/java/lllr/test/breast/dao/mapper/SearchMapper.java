package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Search;

import java.util.List;

public interface SearchMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Search record);

    int insertSelective(Search record);

    Search selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Search record);

    int updateByPrimaryKey(Search record);

    List<String> getHotWords();
}