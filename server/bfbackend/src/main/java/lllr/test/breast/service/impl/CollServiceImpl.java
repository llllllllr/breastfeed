package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.ArticleMapper;
import lllr.test.breast.dao.mapper.CollectionMapper;
import lllr.test.breast.dao.mapper.VedioMapper;
import lllr.test.breast.dataObject.popularization.Article;
import lllr.test.breast.dataObject.popularization.Collection;
import lllr.test.breast.dataObject.popularization.Vedio;
import lllr.test.breast.service.inter.CollService;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollServiceImpl implements CollService {

    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    VedioMapper vedioMapper;

    //添加收藏
    @Override
    public ServerResponse<String> addColl(Collection coll) {


        int rowCount  = collectionMapper.insert(coll);
        if(rowCount <= 0 )
            return ServerResponse.createByErrorMsg("收藏失败");
        return ServerResponse.createBysuccessMsg("收藏成功");

    }

    @Override
    public ServerResponse<String> ifColl(Integer userid, Integer coll_id, Integer type) {
        Collection coll = collectionMapper.ifcoll(userid,coll_id,type);
        if(coll != null)
            return ServerResponse.createBysuccessMsg("已收藏");

        return ServerResponse.createByErrorMsg("未收藏");
    }


    //获取收藏列表
    public ServerResponse<List<?>> getCollections(Integer type,Integer userid) {

        List<Integer> collIds = collectionMapper.getCollectionID(type,userid);
            if(type == 1)
            {
                List<Article> result  = new ArrayList<>();

                for(Integer id : collIds){
                    Article article = articleMapper.selectByPrimaryKey(id);
                    result.add(article);
                }
                return ServerResponse.createBysuccessData(result);
            }

        if(type == 2)
        {
            List<Vedio> result  = new ArrayList<>();

            for(Integer id : collIds){
                Vedio vedio = vedioMapper.selectByPrimaryKey(id);
                result.add(vedio);
            }
            return  ServerResponse.createBysuccessData(result);
        }

        return ServerResponse.createByErrorMsg("没有收藏");
    }

    //取消收藏
    @Override
    public ServerResponse<String> cancelColl(Integer userid,Integer collid) {

        int rowCount  = collectionMapper.cancel(userid,collid);
        if(rowCount <= 0 )
            return ServerResponse.createByErrorMsg("取消收藏失败");
        return ServerResponse.createBysuccessMsg("取消收藏成功");
    }
}
