package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.UserService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.exception.StringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${user.password.length}")
    private int USER_PASSWORD_LENGTH;
    /*
  注册
  1. 接收
  {
    用户填写表单信息
  }

   2.返回
  success{
    status:'0',
    msg:'注册成功!',
    data:[]
  }

  fail{
    status:'1',
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
    public ServerResponse<User> UserRegister(@RequestParam(value="age",required = false)Integer age,
                                         @RequestParam(value="creditId",required = true)String creditId,
                                         @RequestParam(value="pregnantType",required = true)Integer pregnantType,
                                         @RequestParam(value="pregnantWeek",required = true)String pregnantWeek,
                                         @RequestParam(value="job",required = false)String job,
                                         @RequestParam(value="confinementDate",required = true)Date confinementDate,
                                         @RequestParam(value="confinementWeek",required = true)Integer confinementWeek,
                                         @RequestParam(value="confinementType",required = true)Integer confinementType,
                                         @RequestParam(value="userName",required = true) String userName,
                                         @RequestParam(value="userPassword",required = true)String userPassword,
                                             HttpServletRequest requests) throws StringException {

        Map<String,String> errorMap = new HashMap<>();
        User user = new User();
        if (DataValidateUtil.ageValidate(age)) {
            user.setAge(age);
        } else {
            user.setAge(null);
        }
        if (DataValidateUtil.length(creditId,25)) {
            user.setCreditId(creditId);
        } else {
            errorMap.put("creditId","身份证号码错误!");
            user.setCreditId(creditId);
        }

        user.setPregnantType(pregnantType);

        if (!DataValidateUtil.isBlank(pregnantWeek)) {
            user.setPregnantWeek(pregnantWeek);
        } else {
            errorMap.put("pregnantWeek","孕周不能为空！");
            user.setPregnantWeek(null);
        }

        user.setJob(job);

        if (!DataValidateUtil.isNull(confinementDate)) {
            user.setConfinementDate(confinementDate);
        } else {
            errorMap.put("confinementDate","产期不能为空！");
            user.setConfinementDate(null);
        }

        user.setConfinementWeek(confinementWeek);
        user.setConfinementType(confinementType);

        if (!DataValidateUtil.isBlank(userName)) {
            user.setUserName(userName);
        } else {
            errorMap.put("userName","用户名不能为空！");
            user.setUserName(userName);
        }

        if (!DataValidateUtil.length(userPassword,USER_PASSWORD_LENGTH)) {
            user.setUserPassword(userPassword);
        } else {
            errorMap.put("userPassword","密码长度不能小于" + USER_PASSWORD_LENGTH);
            user.setUserPassword(userPassword);
        }

        if(errorMap.size() > 0)
            return new ServerResponse<User>(0,errorMap.toString(),user);

        user.setUserToken(UUID.randomUUID().toString().replace("-",""));
        ServerResponse<User> userResponse = userService.UserRegister(user);
        return userResponse;
    }




}
