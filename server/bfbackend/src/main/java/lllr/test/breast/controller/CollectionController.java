package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Collection;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectionController {

    //添加收藏
    @GetMapping("/coll/addcoll")
    @CrossOrigin
    public ServerResponse<String> addColl(@RequestParam("userId") Integer userId,
                                              @RequestParam("collId") Integer collId,
                                              @RequestParam("type")   Integer type)
    {
        Collection collection = new Collection();
        collection.setCollType(type);
        collection.setCollId(collId);
        collection.setUserId(userId);
        return null;
    }

}
