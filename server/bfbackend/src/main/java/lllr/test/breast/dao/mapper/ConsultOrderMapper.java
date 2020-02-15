package lllr.test.breast.dao.mapper;

import java.util.List;
import lllr.test.breast.dataObject.consult.ConsultOrder;
import lllr.test.breast.dao.mapperUtil.ConsultOrderExample;
import org.apache.ibatis.annotations.Param;

public interface ConsultOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsultOrder record);

    int insertSelective(ConsultOrder record);

    List<ConsultOrder> selectByExample(ConsultOrderExample example);

    ConsultOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsultOrder record, @Param("example") ConsultOrderExample example);

    int updateByExample(@Param("record") ConsultOrder record, @Param("example") ConsultOrderExample example);

    int updateByPrimaryKeySelective(ConsultOrder record);

    int updateByPrimaryKey(ConsultOrder record);
}