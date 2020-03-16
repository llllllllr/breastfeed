package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;

import java.util.List;

public interface AdministratorConsult {
    //添加关键词回答模板
    ServerResponse createTemplate(AutoAnswerTemplate answerTemplate);

    //查询所有回答模板
    ServerResponse<List<AutoAnswerTemplate>> selectAllTemplate();

    //根据主键修改
    ServerResponse updateTemplate(AutoAnswerTemplate autoAnswerTemplate);

    //根据主键删除
    ServerResponse deleteTemplate(Integer consultId);
}
