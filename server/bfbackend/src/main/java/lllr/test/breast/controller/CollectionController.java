package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Collection;
import lllr.test.breast.service.inter.CollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coll")
public class CollectionController {


    @Autowired
    CollService collService;

    //添加收藏
    @GetMapping("/addcoll")
    @CrossOrigin
    public ServerResponse<String> addColl(    @RequestParam("userId") Integer userId,
                                              @RequestParam("collId") Integer collId,
                                              @RequestParam("type")   Integer type)
    {
        Collection collection = new Collection();
        collection.setCollType(type);
        collection.setCollId(collId);
        collection.setUserId(userId);
        return collService.addColl(collection);
    }

    //测试已经收藏
    @GetMapping("/ifcoll")
    @CrossOrigin
    public ServerResponse<String> ifColl(     @RequestParam("userId") Integer userId,
                                              @RequestParam("collId") Integer collId,
                                              @RequestParam("type")   Integer type) {

             return collService.ifColl(userId,collId,type);
    }

    //取消
    @GetMapping("/delcoll")
    @CrossOrigin
    public ServerResponse<String> delColl(    @RequestParam("userId") Integer userId,
                                              @RequestParam("collId") Integer collId)
    {
          return collService.cancelColl(userId,collId);
    }

    //获取收藏列表
    @GetMapping("/getcolls")
    @CrossOrigin
    public ServerResponse<List<?>> getColl(@RequestParam("userId") Integer userId,
                                           @RequestParam("type") Integer type)
    {
        return collService.getCollections(type,userId);
    }
}
