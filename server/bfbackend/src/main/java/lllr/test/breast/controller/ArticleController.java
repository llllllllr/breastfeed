package lllr.test.breast.controller;

import jdk.internal.org.objectweb.asm.TypeReference;
import lllr.test.breast.common.Const;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Article;
import lllr.test.breast.dataObject.popularization.PagedResult;
import lllr.test.breast.redis.ArticleKey;
import lllr.test.breast.redis.KeyPrefix;
import lllr.test.breast.redis.RedisService;
import lllr.test.breast.service.inter.ArticleService;
import lllr.test.breast.util.qiniu.QiniuRes;
import lllr.test.breast.util.qiniu.QiniuResultUtil;
import lllr.test.breast.util.qiniu.QiniuUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ArticleController {


    @Autowired
    ArticleService articleService;
    @Resource
    Const constNum;
    @Resource
    QiniuUtil qiniu;

    @Autowired
    RedisService redisService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    //获取七牛云上传凭证
    @RequestMapping(value="/article/getToken")
    @CrossOrigin
    public String returnToken(@RequestParam("bucket") String bucket){

        String token = qiniu.getToken(bucket);
        return  token;
    }


    //分页查询文章java.util.List<Article.class>
    @GetMapping("/article/getlist")
    @CrossOrigin
    public ServerResponse<List<Article>> queryArticleList()  {

        List<Article> rs = new ArrayList<Article>();
        List<Article> articles = redisService.getlist(ArticleKey.articleList,"",Article.class);
        //取缓存
        if(articles!=null)
           return ServerResponse.createBysuccessData(articles);
        //如果没有缓存，就放入缓存
        else {
            ServerResponse<List<Article>> res = articleService.getArticleList();
            redisService.set(ArticleKey.articleList, "", res.getData());
            LOGGER.info(res.getData().get(0).getTitle());
            return res;
        }
    }


    //根据关键词搜索文章
    @GetMapping("article/search")
    @CrossOrigin
    public ServerResponse<List<Article>> searchArticles(@RequestParam("content") String content)
    {

        return articleService.getArticlesBy(content);
    }


    //获取文章热搜词
    @GetMapping("/article/getHotWords")
    @CrossOrigin
    public ServerResponse<List<String>> getHotWords()
    {
        return articleService.getHotWords();
    }
    //物理分页查询列表
    @GetMapping("/article/getlist_page")
    @CrossOrigin
    public ServerResponse<PagedResult> queryArticleList_page(@RequestParam("page") Integer page){

        //可能传过来的页数是空
        if(page == null)
            page = 1;
        //常熟
        return articleService.getAllArticles(page,constNum.PAGE_SIZE);
    }

    //获取一篇文章
    @GetMapping("/article/getone")
    @CrossOrigin
    public ServerResponse<Article> queryArticle(@RequestParam("id")  Integer id){
        //logger.info("id= {} ",id);
        return articleService.queryById(id);
    }

    //删除一篇文章
    @GetMapping("/article/del")
    @CrossOrigin
    public ServerResponse<String> delArticle(@RequestParam("id") Integer id){
        return articleService.delArticle(id);
    }
    //新增或修改文章 GetMapping不支持@RequestBody
    @GetMapping("/article/addedit")
    @CrossOrigin
    public ServerResponse<String> addArticle(@RequestParam("id")Integer id,
                                             @RequestParam("title")String title,
                                             @RequestParam("subtitle")String subtitle,
                                             @RequestParam("imgurl") String imgurl,
                                             @RequestParam("content")String content,
                                             @RequestParam("category")String category) {

        Article article = new Article();
        if(id!=-1)
            article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        article.setCategory(category);
        article.setDescription(subtitle);
        article.setImgUrl(imgurl);
        return articleService.addeditArticle(article);

    }

    //上传图片
    @RequestMapping(value = "/article/upload",method = RequestMethod.POST)
    @CrossOrigin
    public QiniuRes uploadImg(MultipartFile multiple){

        if(multiple != null) {
            try{
                FileInputStream inputStream =(FileInputStream)multiple.getInputStream();
                String fileName = multiple.getOriginalFilename();
                String path = qiniu.upLoeadToken(inputStream,fileName,"wdtc");
                System.out.println(path);
                String[] data ={path};
                QiniuResultUtil qiniuResultUtil = new QiniuResultUtil();
                return qiniuResultUtil.success(data);

            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        return null;
    }
}
