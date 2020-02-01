package lllr.test.breast.service.inter;

import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;

import java.util.List;

public interface AdministratorConsult {
    //添加关键词回答模板
    boolean createTemplate(AutoAnswerTemplate answerTemplate);

    //查询所有回答模板
    List<AutoAnswerTemplate> selectAllTemplate();

    //根据主键修改
    boolean updateTemplate(AutoAnswerTemplate autoAnswerTemplate);

    //根据主键删除
    boolean deleteTemplate(Integer consultId);
}
