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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    public ServerResponse CreateTemplate(@RequestParam(value="questionKey")@NotEmpty(message = "关键词不能为空！") String questionKey,
                                         @RequestParam(value="answerTemplate")@NotEmpty(message = "答案模板不能为空！") String answerTemplate){

        AutoAnswerTemplate autoAnswerTemplate = new AutoAnswerTemplate(questionKey,answerTemplate);
        LOGER.debug("=== administratorCreate :" + autoAnswerTemplate + "===");

        return administratorConsult.createTemplate(autoAnswerTemplate);

    }

    //收索所有后台管理人员关键词的回答模板
    @RequestMapping("/selectAll")
    public ServerResponse<List<AutoAnswerTemplate>> SelectAll(){
        return administratorConsult.selectAllTemplate();
    }

    //更改部分信息
    @RequestMapping("/update")
    public ServerResponse update(@RequestParam(value="consultId")@NotNull Integer consultId,
                                 @RequestParam(value="questionKey")@NotEmpty(message = "问题不能为空！")String questionKey,
                                 @RequestParam(value="answerTemplate")@NotEmpty(message = "答案模板不能为空！")String answerTemplate){
        AutoAnswerTemplate autoAnswerTemplate = new AutoAnswerTemplate(consultId,questionKey,answerTemplate);

        return administratorConsult.updateTemplate(autoAnswerTemplate);

    }

    //删除关键词的回答模板
    @RequestMapping("/delete")
    public ServerResponse DeleteTemplate(@RequestParam(value="consultId") Integer consultId){
        if(consultId == null){
            return ServerResponse.createByErrorMsg("删除失败！");
        }

        return administratorConsult.deleteTemplate(consultId);

    }
}
