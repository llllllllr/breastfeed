package lllr.test.breast.service.Inte;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.Article;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {


    //增加文章
    ServerResponse<String> addArticle(Article article);

}
