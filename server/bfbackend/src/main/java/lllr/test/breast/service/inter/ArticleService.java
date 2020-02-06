package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {


        //添加或修改文章
    ServerResponse<String> addeditArticle(Article article);

    //获取文章列表
    ServerResponse<List<Article>> getArticleList();

    //获取一篇文章
    ServerResponse<Article> queryById(Integer id);

    //删除一篇文章
    ServerResponse<String>  delArticle(Integer id);

}
