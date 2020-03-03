package lllr.test.breast.service.inter;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Collection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollService {

    //添加收藏
     ServerResponse<String> addColl(Collection coll);


     //判断是否已经收藏
     ServerResponse<String> ifColl(Integer userid,Integer coll_id,Integer type);
     //获取收藏列表
     ServerResponse<List<?>> getCollections(Integer type,Integer userid);

    //取消收藏
    ServerResponse<String> cancelColl(Integer userid,Integer collid);
}
