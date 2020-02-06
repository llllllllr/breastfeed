package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Article;

import java.util.List;

public interface ArticleMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> selectArticleList();
}