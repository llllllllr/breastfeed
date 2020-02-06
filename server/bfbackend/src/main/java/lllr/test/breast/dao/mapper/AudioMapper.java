package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.popularization.Audio;

import java.util.List;

public interface AudioMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Audio record);

    int insertSelective(Audio record);

    Audio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Audio record);

    int updateByPrimaryKey(Audio record);

    List<Audio> selectAudioList();
}