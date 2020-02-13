package lllr.test.breast.service.inter;

import com.github.pagehelper.PageInfo;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Article;
import lllr.test.breast.dataObject.popularization.PagedResult;
import org.springframework.stereotype.Service;
import scala.Int;

import java.util.List;

@Service
public interface ArticleService {

    //获取热搜词
    ServerResponse<List<String>> getHotWords();
    //分页获取文章列表
    ServerResponse<PagedResult> getAllArticles(Integer page, Integer pageSize);

    //添加查询词以查询热搜词并根据查询词获取文章列表
    ServerResponse<List<Article>> getArticlesBy(String content);

    //添加或修改文章
    ServerResponse<String> addeditArticle(Article article);

    //获取文章列表
    ServerResponse<List<Article>> getArticleList();

    //获取一篇文章
    ServerResponse<Article> queryById(Integer id);

    //删除一篇文章
    ServerResponse<String>  delArticle(Integer id);

}
