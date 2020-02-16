package lllr.test.breast.service.inter;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Collection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollService {

    //添加收藏
     ServerResponse<String> addColl(Collection coll);


     //获取收藏列表
    ServerResponse<List<Collection>> getCollections(Integer type);

    //取消收藏
    ServerResponse<String> cancelColl(Integer id);
}
