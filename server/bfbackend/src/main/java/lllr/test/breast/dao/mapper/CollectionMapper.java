package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Collection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(Collection record);
//
//    int updateByPrimaryKey(Collection record);

    List<Collection> getCollections();

    List<Integer> getCollectionID(Integer type);
}