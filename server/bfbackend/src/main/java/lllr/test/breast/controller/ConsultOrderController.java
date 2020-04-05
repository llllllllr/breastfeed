package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.ConsultOrder;
import lllr.test.breast.service.impl.ConsultOrderServiceImpl;
import lllr.test.breast.service.inter.ConsultOrderService;
import lllr.test.breast.util.DataValidateUtil;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Validated
@ResponseBody
@Controller
public class ConsultOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultOrderServiceImpl.class);

    @Autowired
    private ConsultOrderService consultOrderService;

    @GetMapping("/selectConsultOrderByDoctorId")
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByDoctorId(@RequestParam(value="doctorId")@NotNull Integer doctorId){
        return consultOrderService.selectConsultOrderByDoctorId(doctorId);
    }

    @GetMapping("/updateConsultOrderStatusById")
    public ServerResponse updateConsultOrderStatusById(@RequestParam(value="status")@NotNull Integer status,@RequestParam(value="id")@NotNull Integer id){
        return consultOrderService.updateConsultOrderStatusById(id,status);
    }

    @GetMapping("/selectConsultOrderByUserId")
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByUserId(@RequestParam(value="userId")@NotNull Integer userId){
        return consultOrderService.selectConsultOrderByUserId(userId);
    }

    @RequestMapping("/addConsultOrder")
    public ServerResponse<String> AddConsultOrder(@RequestParam(value="doctorId")Integer doctorId,
                                          @RequestParam(value="userId")Integer userId,
                                          @RequestParam(value="createTime") String createTime,
                                          @RequestParam(value="lastingTime")Integer lastingTime,
                                          @RequestParam(value="contact",required = false)String contact,
                                          @RequestParam(value="contactPhone")@Length(min=6 , max=16,message = "联系人电话格式错误!") String contactPhone,
                                          @RequestParam(value="symptomDescription")@NotEmpty(message = "症状描述不能为空！") String symptomDescription,
                                          @RequestParam(value="consultCost",required = false)Integer consultCost,
                                          @RequestParam(value = "userOpenId",required = false)String userOpenId,
                                          @RequestParam(value = "doctorOpenId",required = false)String doctorOpenId,
                                          @RequestParam(value = "imgUrls",required = false)String imgUrls){
        //转换日期的类型
        Date create_time = null;
        try {
            create_time = DataValidateUtil.StringToDetailedDate(createTime);
        } catch (ParseException e) {
            LOGGER.error("=== CreateConsultOrder：" + createTime + " 日期转换错误! ===");
            return ServerResponse.createByErrorMsg("数据格式错误！");
        }

        ConsultOrder consultOrder = new ConsultOrder(doctorId,userId,create_time,lastingTime,contact,contactPhone,symptomDescription,consultCost);
        consultOrder.setOid(UUID.randomUUID().toString().replace("-", ""));
        consultOrder.setUserOpenId(userOpenId);
        consultOrder.setDoctorOpenId(doctorOpenId);
        consultOrder.setImgUrls(imgUrls);

        LOGGER.info("=== AddConsultOrder:" + consultOrder + " ===");

        return consultOrderService.AddConsultOrder(consultOrder);

    }

    @GetMapping("/getByOid")
    ServerResponse<ConsultOrder> getByOid(@RequestParam("oid")@NotEmpty String oid)
    {
        return consultOrderService.selectByOid(oid);
    }
}
