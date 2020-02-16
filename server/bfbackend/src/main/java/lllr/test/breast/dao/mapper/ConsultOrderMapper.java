package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.consult.ConsultOrder;

import java.util.List;

public interface ConsultOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsultOrder record);

    int insertSelective(ConsultOrder record);

    ConsultOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsultOrder record);

    int updateByPrimaryKey(ConsultOrder record);

    List<ConsultOrder> selectConsultOrderAndUserByUserId(Integer userId);

    List<ConsultOrder> selectConsultOrderAndDoctorByDoctorId(Integer userId);
}