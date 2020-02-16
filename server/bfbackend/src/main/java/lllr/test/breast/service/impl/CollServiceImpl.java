package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.ArticleMapper;
import lllr.test.breast.dao.mapper.CollectionMapper;
import lllr.test.breast.dao.mapper.VedioMapper;
import lllr.test.breast.dataObject.popularization.Article;
import lllr.test.breast.dataObject.popularization.Collection;
import lllr.test.breast.dataObject.popularization.Vedio;
import lllr.test.breast.service.inter.CollService;
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

    //获取收藏列表
    @Override
    public ServerResponse<List<Collection>> getCollections(Integer type) {

        List<Integer> collIds = collectionMapper.getCollectionID(type);
        if(type == 1)
        {
            List<Article> result  = new ArrayList<>();

            for(Integer id : collIds){
                Article article = articleMapper.selectByPrimaryKey(id);
                result.add(article);
            }
        }

        if(type == 2)
        {
            List<Vedio> result  = new ArrayList<>();

            for(Integer id : collIds){
                Vedio vedio = vedioMapper.selectByPrimaryKey(id);
                result.add(vedio);
            }
        }

        List<Collection> collections = collectionMapper.getCollections();
        return ServerResponse.createBysuccessData(collections);
    }

    //取消收藏
    @Override
    public ServerResponse<String> cancelColl(Integer id) {

        int rowCount  = collectionMapper.deleteByPrimaryKey(id);
        if(rowCount <= 0 )
            return ServerResponse.createByErrorMsg("取消收藏失败");
        return ServerResponse.createBysuccessMsg("取消收藏成功");
    }
}
