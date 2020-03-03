package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionMapper {



    //取消收藏
    int cancel(@Param("userid")Integer userid,@Param("collid")Integer collid);
    int deleteByPrimaryKey(Integer id);

    //是否已经收藏
    Collection ifcoll(@Param("userid") Integer userid, @Param("collid")Integer collid,@Param("type") Integer type);
    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(Collection record);
//
//    int updateByPrimaryKey(Collection record);

    List<Collection> getCollections();

    List<Integer> getCollectionID(@Param("type") Integer type,@Param("userid")Integer userid);
}