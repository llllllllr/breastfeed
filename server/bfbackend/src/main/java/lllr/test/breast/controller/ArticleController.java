package lllr.test.breast.controller;

import com.mysql.fabric.Server;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.Article;
import lllr.test.breast.service.ArticleService;
import lllr.test.breast.util.QiniuRes;
import lllr.test.breast.util.QiniuResultUtil;
import lllr.test.breast.util.QiniuUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;


@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Resource
    QiniuUtil qiniu;

    @GetMapping("/article/add")
    @ResponseBody
    public ServerResponse<String> addArticle(@RequestParam("title")String title,
                                             @RequestParam("subtitle")String subtitle,
                                             @RequestParam("content")String content){
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCategory("category");
        article.setDescription(subtitle);
        article.setImgUrl("imgurl");
        System.out.println(title);
        return articleService.addArticle(article);
    }

    @RequestMapping(value = "/article/upload",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public QiniuRes uploadImg(MultipartFile multiple){

        System.out.println("heheheheheheh");
        if(multiple != null){
            try{
                FileInputStream inputStream =(FileInputStream)multiple.getInputStream();
                String fileName = multiple.getOriginalFilename();
                String path = qiniu.upLoeadToken(inputStream,fileName);
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
