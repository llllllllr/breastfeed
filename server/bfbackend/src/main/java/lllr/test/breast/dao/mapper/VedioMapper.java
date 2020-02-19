package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Vedio;

import java.util.List;

public interface VedioMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vedio record);

    int insertSelective(Vedio record);

    Vedio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vedio record);

    int updateByPrimaryKey(Vedio record);

    List<Vedio> getVedioList();
}