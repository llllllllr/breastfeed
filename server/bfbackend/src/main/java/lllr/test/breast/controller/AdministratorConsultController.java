package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.AutoAnswerTemplate;
import lllr.test.breast.service.inter.AdministratorConsult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/administrator/consult")
public class AdministratorConsultController {
    private static final Logger LOGER = LoggerFactory.getLogger(AdministratorConsultController.class);

    @Autowired
    private AdministratorConsult administratorConsult;

    //后台管理人员添加关键词的回答模板
    @RequestMapping("/create")
    public ServerResponse CreateTemplate(@RequestParam(value="questionKey")String questionKey, @RequestParam(value="answerTemplate")String answerTemplate){
        //数据校验
        if(questionKey.trim().length() < 1 || answerTemplate.trim().length() < 1) {
            LOGER.error("=== CreateTemplate：关键词和答案模板不能为空 ===");
            return ServerResponse.createByErrorMsg("关键词和答案模板不能为空！");
        }

        AutoAnswerTemplate autoAnswerTemplate = new AutoAnswerTemplate(questionKey,answerTemplate);
        LOGER.debug("=== administratorCreate :" + autoAnswerTemplate + "===");

        if(administratorConsult.createTemplate(autoAnswerTemplate))
        {
            LOGER.debug("=== administratorCreate :" + autoAnswerTemplate + "添加成功 ===");
            return ServerResponse.createBysuccessMsg("添加成功!");
        }

        LOGER.debug("=== administratorCreate :" + autoAnswerTemplate + "添加失败 ===");
        return ServerResponse.createByErrorMsg("添加失败！");
    }

    //收索所有后台管理人员关键词的回答模板
    @RequestMapping("/selectAll")
    public ServerResponse SelectAll(){
        List<AutoAnswerTemplate> templates = administratorConsult.selectAllTemplate();
        LOGER.debug("=== administratorSelectAll :" + templates.toString() + "===");
        return ServerResponse.createBysuccessData(templates);
    }

    //更改部分信息
    @RequestMapping("/update")
    public ServerResponse update(@RequestParam(value="consultId")Integer consultId,@RequestParam(value="questionKey")String questionKey,
                                 @RequestParam(value="answerTemplate")String answerTemplate){
        AutoAnswerTemplate autoAnswerTemplate = new AutoAnswerTemplate(consultId,questionKey,answerTemplate);
        if(consultId == null || questionKey.trim().length() == 0 || answerTemplate.trim().length() == 0)
        {
            LOGER.debug("=== administrator update:" + answerTemplate + " 失败 ===");
            return ServerResponse.createByErrorMsg("添加失败");
        }

        if(administratorConsult.updateTemplate(autoAnswerTemplate))
        {
            LOGER.debug("=== administrator update:" + autoAnswerTemplate + " 失败 ===");
            return ServerResponse.createBysuccessMsg("修改成功!");
        }

        LOGER.debug("=== administrator update:" + autoAnswerTemplate + " 成功 ===");
        return ServerResponse.createBysuccessMsg("修改失败!");


    }

    //删除关键词的回答模板
    @RequestMapping("/delete")
    public ServerResponse DeleteTemplate(@RequestParam(value="consultId") Integer consultId){
        if(consultId == null){
            return ServerResponse.createByErrorMsg("删除失败！");
        }

        if(administratorConsult.deleteTemplate(consultId)){
            LOGER.debug(" === administrator delete consultId:" + consultId + " 成功 === ");
            return ServerResponse.createBysuccessMsg("删除成功!");
        }

        LOGER.debug(" === administrator delete consultId:" + consultId + " 失败 === ");
        return ServerResponse.createByErrorMsg("删除失败");
    }
}
