package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Audio;
import lllr.test.breast.service.inter.AudioService;
import lllr.test.breast.util.qiniu.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AudioController {
    @Autowired
    AudioService audioService;
    @Resource
    QiniuUtil qiniu;


    //获取单个音频
    @GetMapping("audio/getone")
    @CrossOrigin
    public ServerResponse<Audio> getAudio(@RequestParam("id")Integer id)
    {
        return audioService.queryById(id);
    }

    //添加或删除音频
    @GetMapping("/audio/add_editAudio")
    @CrossOrigin
    public ServerResponse<String> addeditAuido  (@RequestParam("id")Integer id,
                                                 @RequestParam("title")String title,
                                                 @RequestParam("adourl")String adourl,
                                                 @RequestParam("imgurl")String imgurl,
                                                 @RequestParam("category")String category) {

        Audio audio = new Audio();
        audio.setTitle(title);
        audio.setCategory(category);
        audio.setCoverUrl(imgurl);
        audio.setAudiourl(adourl);
        if(id != -1)
            audio.setId(id);
        ServerResponse<String> res = audioService.addeditAudio(audio);
        System.out.println(res.getMsg());
        return res;
    }

    //获取列表
    @GetMapping("/audio/getlist")
    @CrossOrigin
    public ServerResponse<List<Audio>> getAudioList()
    {
        return audioService.getAudioList();
    }

    //删除
    @GetMapping("/audio/delAudio")
    @CrossOrigin
    public ServerResponse<String> delAudio(@RequestParam("id") Integer id)
    {
        return audioService.delAudio(id);
    }
}
