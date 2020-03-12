package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.AutoAnswerTemplateMapper;
import lllr.test.breast.dao.mapperUtil.AutoAnswerTemplateExample;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.service.inter.AdministratorConsult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorConsultServiceImpl implements AdministratorConsult {
    @Autowired
    private AutoAnswerTemplateMapper autoAnswerTemplateMapper;

    @Override
    public ServerResponse createTemplate(AutoAnswerTemplate answerTemplate) {
        return autoAnswerTemplateMapper.insertSelective(answerTemplate) == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByError();
    }

    @Override
    public ServerResponse<List<AutoAnswerTemplate>> selectAllTemplate() {
        return ServerResponse.createBysuccessData(autoAnswerTemplateMapper.selectByExample(new AutoAnswerTemplateExample()));
    }

    @Override
    public ServerResponse updateTemplate(AutoAnswerTemplate autoAnswerTemplate) {
        return autoAnswerTemplateMapper.updateByPrimaryKeySelective(autoAnswerTemplate) == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByError();
    }

    @Override
    public ServerResponse deleteTemplate(Integer consultId) {
        return autoAnswerTemplateMapper.deleteByPrimaryKey(consultId) == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByError();
    }


}
