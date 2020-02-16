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

    //普通用户查询订单（订单包括医生基本信息）
    List<ConsultOrder> selectConsultOrderAndUserByUserId(Integer userId);

    //医生查询订单（订单包括用户基本信息）
    List<ConsultOrder> selectConsultOrderAndDoctorByDoctorId(Integer userId);
}