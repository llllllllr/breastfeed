package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.service.inter.DoctorService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.exception.StringException;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Validated
@RequestMapping("/doctor")
@ResponseBody
@Controller
public class DoctorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/findPassword")
    public ServerResponse UserFindPasswordByLicenseNumber(@RequestParam(value = "licenseNumber")@NotEmpty(message = "职业执照不能为空") String licenseNumber,
                                                     @RequestParam(value = "password")@Length(min=6,message = "密码长度错误") String password){
        return doctorService.UserFindPasswordByLicenseNumber(licenseNumber,password);
    }

    /*
  注册
  1. 接收
  {
    用户填写表单信息
  }

   2.返回
  success{
    status:'1',
    msg:'注册成功!',
    data:[]
  }

  fail{
    status:'0',
    msg:'注册失败'+失败原因
    data:[
        user[表单信息]
    ]
  }

 */
    //注册  /register
    /*
    1.校验表单信息 ->不合格 设置错误信息 保存表单数据 返回注册页面
    2.查询用户名、身份证是否已注册 ->不合格 设置错误信息 保存表单数据 返回注册页面
    3.插入用户信息 ->成功  ->  保存用户相关信息与session，返回注册成功信息 ，转发到首页
                 ->失败  ->   设置错误信息 保存表单数据 返回注册页面
     */

    @GetMapping("/register")
    public ServerResponse<Doctor> DoctorRegister(@RequestParam(value = "licenseNumber")@NotEmpty(message = "职业执照不能为空") String licenseNumber,
                                                @RequestParam(value = "name")@NotEmpty(message = "姓名不能为空") String name,
                                                @RequestParam(value = "userName")@NotEmpty(message = "昵称不能为空") String userName,
                                                @RequestParam(value = "userPassword")@Length(min=6,message = "密码长度错误") String userPassword,
                                                 @RequestParam(value = "imgUrl",required = false) String imgUrl,
                                                 @RequestParam(value = "expertIn",required = false) String expertIn,
                                                 @RequestParam(value = "imagetextCost",required = false) Integer imagetextCost,
                                                 @RequestParam(value = "voiceCost",required = false) Integer voiceCost,
                                                 @RequestParam(value = "videoCost",required = false) Integer videoCost,
                                                 @RequestParam(value = "openId",required = false)String openId,
                                                 HttpServletRequest request, HttpServletResponse response) throws StringException {

        Doctor doctor = new Doctor();
        String token = UUID.randomUUID().toString().replace("-", "");
        doctor.setToken(token);
        doctor.setOpenId(openId);
        doctor.setExpertIn(expertIn);
        doctor.setImagetextCost(imagetextCost);
        doctor.setVideoCost(videoCost);
        doctor.setUserPassword(userPassword);
        doctor.setVoiceCost(voiceCost);
        doctor.setImgUrl(imgUrl);
        doctor.setLicenseNumber(licenseNumber);
        doctor.setName(name);
        doctor.setUserName(userName);

        //将用户的信息插入数据库

//        if (reData.getStatus() == 1) {
//            AfterSign(request, response, doctor);                                                    //注册成功后的相关操作
//        }

        return doctorService.doctorRegister(doctor);
    }

    //在登录成功或者注册成功
    //在Header中添加doctor_token_cookie
    //在session中加入用户信息
    private void AfterSign(HttpServletRequest request, HttpServletResponse response,@NotNull Doctor doctor) {
        response.setHeader("doctor_token",doctor.getToken());
        response.setHeader("doctor_token_date", String.valueOf(System.currentTimeMillis() + 60 * 60 * 24 * 1000));           //设置token 过期时间


//        Cookie doctor_token_cookie = new Cookie("doctor_token", doctor.getToken());
//        doctor_token_cookie.setMaxAge(10 * 60);
//        doctor_token_cookie.setPath("/doctor/tokenSign");
//        response.addCookie(doctor_token_cookie);
    }

    /*+
        登录
        1. 接收
  {
    用户名
    密码

  }

   2.返回
  success{
    status:'1',
    msg:'登录成功!',
    data:[]
  }

  fail{
    status:'0',
    msg:'登录失败'+原因
    data:[]
  }

     */

    @GetMapping("/sign")
    public ServerResponse<Doctor> DoctorSign(@RequestParam(value = "userName")@NotEmpty(message = "用户名不能为空") String userName,
                                     @RequestParam(value = "userPassword")@Length(min=6,message = "密码错误") String userPassword,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {
        //根据用户名查询用户信息并返回
        ServerResponse<Doctor> reData = doctorService.doctorSign(userName, userPassword);

        //不为空说明表单数据正确
        if (reData.getData() != null)
            AfterSign(request, response, reData.getData());

        //为空说明用户名或者密码错误
        return reData;

    }

    //持续化doctor_token免登录
    @RequestMapping("/tokenSign")
    public ServerResponse<Doctor> doctorTokenSign(@RequestParam(value = "doctor_token")@NotEmpty String doctor_token,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        ServerResponse<Doctor> reData = doctorService.doctorTokenSign(doctor_token);
        if(reData.getData() != null)
            AfterSign(request, response, reData.getData());
        return reData;
    }

//    //用户退出
//    @GetMapping("/exit")
//    public ServerResponse exit(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().removeAttribute("userName");
//        //将浏览器的user_token 置空
//        Cookie cookie = new Cookie("doctor_token", null);
//        cookie.setPath("/doctor/tokenSign");
//        response.addCookie(cookie);
//
//        return ServerResponse.createBysuccess();
//    }

    /*
    #################### 修改医生的字段后没有测试

     */
    //医生修改自己的咨询费用
    @GetMapping("/updateConsultCost")
    public ServerResponse updateConsultCost(@RequestParam(value = "doctorId") Integer doctorId,
                                            @RequestParam(value="consultCost") Integer consultCost){
        if(DataValidateUtil.isNull(doctorId) || DataValidateUtil.isNull(consultCost))
            return ServerResponse.createByError();

        return doctorService.updateConsultCost(doctorId,consultCost);


    }

    //查询所有医生
    @GetMapping("/getAllDoctor")
    public ServerResponse<List<Doctor>> getAllDoctor(){
        return doctorService.getAllDoctor();
    }

}
