package lllr.test.breast.service.Impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.ArticleMapper;
import lllr.test.breast.dataObject.Article;
import lllr.test.breast.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public ServerResponse<String> addArticle(Article article) {

        if(article == null)
            return ServerResponse.createByErrorMsg("article 参数错误");
        int insertCount = articleMapper.insert(article);
        if(insertCount > 0){
            return ServerResponse.createBysuccessMsg("插入文章成功");
        }else{
            return ServerResponse.createByErrorMsg("插入文章失败");
        }
    }
}
