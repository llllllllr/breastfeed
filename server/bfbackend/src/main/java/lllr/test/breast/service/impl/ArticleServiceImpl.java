package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.ArticleMapper;
import lllr.test.breast.dataObject.popularization.Article;
import lllr.test.breast.service.inter.ArticleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    ArticleMapper articleMapper;


    @Override
    public ServerResponse<String>  delArticle(Integer id) {
        if(id == null)
            return ServerResponse.createBysuccessMsg("id参数错误");
        int rowCount;
        rowCount = articleMapper.deleteByPrimaryKey(id);
        if(rowCount > 0)
            return ServerResponse.createBysuccessMsg("删除文章成功");
        return  ServerResponse.createByErrorMsg("删除文章失败");

    }
    //获取一篇文章
    @Override
    public ServerResponse<Article> queryById(Integer id) {
        if(id==null)
            return ServerResponse.createByErrorMsg("id参数错误");
        Article res = articleMapper.selectByPrimaryKey(id);
        // logger.info("return article = {}",res.getTitle());
        return ServerResponse.createBysuccessData(res);
    }
    @Override
    public ServerResponse<String> addeditArticle(Article article) {

        //传过来的参数article错误，返回参数错误
        if(article == null)
            return ServerResponse.createByErrorMsg("article 参数错误");

        int rowCount;
        Integer id = article.getId();
        //如果该文章已经存在，那么修改文章
        if(id != null){
            //更新文章
            rowCount = articleMapper.updateByPrimaryKey(article);
            //检查是否更新文章成功
            if(rowCount > 0)
                return ServerResponse.createBysuccessMsg("更新文章成功");
            else
                return ServerResponse.createByErrorMsg("更新文章失败");

        }else{
            //新增文章
            rowCount = articleMapper.insert(article);
            //检查是否新增文章成功
            if(rowCount > 0)
                return ServerResponse.createBysuccessMsg("新增文章成功");
            else
                return ServerResponse.createByErrorMsg("新增文章失败");

        }
    }
    @Override
    public ServerResponse<List<Article>> getArticleList() {
        List<Article> articleList = articleMapper.selectArticleList();
        for(int i=0;i<articleList.size();i++)
            if(articleList.get(i).getTitle().length()==0){
                articleList.remove(articleList.get(i));
            }
        return ServerResponse.createBysuccessData(articleList);
    }
}
