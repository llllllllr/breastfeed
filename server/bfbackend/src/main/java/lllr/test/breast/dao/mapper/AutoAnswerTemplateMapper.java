package lllr.test.breast.dao.mapper;

import java.util.List;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.dao.mapperUtil.AutoAnswerTemplateExample;
import org.apache.ibatis.annotations.Param;

public interface AutoAnswerTemplateMapper {
    long countByExample(AutoAnswerTemplateExample example);

    int deleteByExample(AutoAnswerTemplateExample example);

    int deleteByPrimaryKey(Integer consultId);

    int insert(AutoAnswerTemplate record);

    int insertSelective(AutoAnswerTemplate record);

    List<AutoAnswerTemplate> selectByExample(AutoAnswerTemplateExample example);

    AutoAnswerTemplate selectByPrimaryKey(Integer consultId);

    int updateByExampleSelective(@Param("record") AutoAnswerTemplate record, @Param("example") AutoAnswerTemplateExample example);

    int updateByExample(@Param("record") AutoAnswerTemplate record, @Param("example") AutoAnswerTemplateExample example);

    int updateByPrimaryKeySelective(AutoAnswerTemplate record);

    int updateByPrimaryKey(AutoAnswerTemplate record);
}