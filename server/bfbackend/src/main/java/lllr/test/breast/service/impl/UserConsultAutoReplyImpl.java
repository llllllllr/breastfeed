package lllr.test.breast.service.impl;

import lllr.test.breast.dao.mapper.AutoAnswerTemplateMapper;
import lllr.test.breast.dao.mapperUtil.AutoAnswerTemplateExample;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.service.inter.UserConsultAutoReply;
import lllr.test.breast.util.ikanalyzer.IKAnalyzerUtil;
import lllr.test.breast.util.wx.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserConsultAutoReplyImpl implements UserConsultAutoReply {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsultAutoReplyImpl.class);

    @Autowired
    private AutoAnswerTemplateMapper autoAnswerTemplateMapper;


    //和微信官方交互
    @Autowired
    private WXUtil wxUtil;

    @Override
    public List<AutoAnswerTemplate> AutoReply(String message) {
        //从用户的问题中抽取关键词
        List<String> key_words = IKAnalyzerUtil.iKSegmenterToList(message);

        AutoAnswerTemplateExample autoAnswerTemplateExample = new AutoAnswerTemplateExample();
        //构造 模糊查询 条件
        for(String temp:key_words) {
            AutoAnswerTemplateExample.Criteria criteria = autoAnswerTemplateExample.createCriteria().andQuestionKeyLike("%" + temp + "%");
            autoAnswerTemplateExample.or(criteria);
        }
        List<AutoAnswerTemplate> templates =  autoAnswerTemplateMapper.selectByExample(autoAnswerTemplateExample);

        LOGGER.info("=== " + message + "  查询得到系统自动回复 :" + templates.toString() + " ===");


        return templates;
    }

//    /*
//
//    ############### 没有域名 未调试
//     */
//    @Override
//    public Object AutoReply(Map questionContent) {
//        String question = null;
//        if(((String)questionContent.get("MsgType")).equalsIgnoreCase("text"))
//            question = (String) questionContent.get("Content");
//
//        LOGGER.debug(" ===WXAutoReplyService 用户提问内容:" + questionContent + "===");
//        return "成功接收";
//    }


}
