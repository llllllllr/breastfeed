package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Vedio;
import lllr.test.breast.service.inter.VedioService;
import lllr.test.breast.util.qiniu.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class VedioController {
    @Autowired
    VedioService vedioService;
    @Resource
    QiniuUtil qiniu;

    //新增视频
    @GetMapping("/vedio/addedit_vedio")
    @CrossOrigin
    public ServerResponse<String> addEditVedio(@RequestParam("id")Integer id,
                                               @RequestParam("title")String title,
                                               @RequestParam("vdourl")String vdourl,
                                               @RequestParam("imgurl")String imgurl,
                                               @RequestParam("category")String category)
    {
        Vedio vedio = new Vedio();
        if(id != -1)
            vedio.setId(id);
        vedio.setCategory(category);
        vedio.setCoverUrl(imgurl);
        vedio.setTitle(title);
        vedio.setVediourl(vdourl);
        return vedioService.addeditVedio(vedio);
    }
    @GetMapping("/vedio/getlist")
    @CrossOrigin
    public ServerResponse<List<Vedio>> getVedioList()
    {
        return vedioService.getVedioList();
    }


    //获取单个视频
    @GetMapping("/vedio/getone")
    @CrossOrigin
    public ServerResponse<Vedio> getVedio(@RequestParam("id") Integer id)
    {
        return  vedioService.queryById(id);
    }

    //删除单个视频
    @GetMapping("/vedio/del")
    @CrossOrigin
    public ServerResponse<String> delVedio(@RequestParam("id") Integer id)
    {
        return vedioService.delVedio(id);
    }
}
