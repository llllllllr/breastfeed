package lllr.test.breast.service.impl;

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
    public boolean createTemplate(AutoAnswerTemplate answerTemplate) {
        return autoAnswerTemplateMapper.insertSelective(answerTemplate) > 0;
    }

    @Override
    public List<AutoAnswerTemplate> selectAllTemplate() {
        return autoAnswerTemplateMapper.selectByExample(new AutoAnswerTemplateExample());
    }

    @Override
    public boolean updateTemplate(AutoAnswerTemplate autoAnswerTemplate) {
        return autoAnswerTemplateMapper.updateByPrimaryKeySelective(autoAnswerTemplate) > 0;
    }

    @Override
    public boolean deleteTemplate(Integer consultId) {
        return autoAnswerTemplateMapper.deleteByPrimaryKey(consultId) > 0;
    }


}
