package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.UserService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.exception.StringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${user.password.length=8}")
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
    @PostMapping("/register")
    public ServerResponse<User> UserRegister(@RequestParam(value="age",required = false)Integer age,
                                         @RequestParam(value="creditId",required = true)String creditId,
                                         @RequestParam(value="pregnantType",required = true)Integer pregnantType,
                                         @RequestParam(value="pregnantWeek",required = true)String pregnantWeek,
                                         @RequestParam(value="job",required = false)String job,
                                         @RequestParam(value="confinementDate",required = true)Date confinementDate,
                                         @RequestParam(value="confinementWeek",required = true)Integer confinementWeek,
                                         @RequestParam(value="confinementType",required = true)Integer confinementType,
                                         @RequestParam(value="userName",required = true) String userName,
                                         @RequestParam(value="userPassword",required = true)String userPassword) throws StringException {

        List<String> errorList = new ArrayList<>();
        User user = new User();
        if (DataValidateUtil.ageValidate(age)) {
            user.setAge(age);
        } else {
            user.setAge(null);
        }
        if (DataValidateUtil.length(creditId,25)) {
            user.setCreditId(creditId);
        } else {
            errorList.add("身份证号码错误!");
            user.setCreditId(creditId);
        }

        user.setPregnantType(pregnantType);

        if (!DataValidateUtil.isBlank(pregnantWeek)) {
            user.setPregnantWeek(pregnantWeek);
        } else {
            errorList.add("孕周不能为空！");
            user.setPregnantWeek(null);
        }

        user.setJob(job);

        if (!DataValidateUtil.isNull(confinementDate)) {
            user.setConfinementDate(confinementDate);
        } else {
            errorList.add("产期不能为空！");
            user.setConfinementDate(null);
        }

        user.setPregnantType(confinementWeek);
        user.setPregnantType(confinementType);

        if (!DataValidateUtil.isBlank(userName)) {
            user.setUserName(userName);
        } else {
            errorList.add("用户名不能为空！");
            user.setUserName(userName);
        }

        if (!DataValidateUtil.length(userPassword,USER_PASSWORD_LENGTH)) {
            user.setUserPassword(userPassword);
        } else {
            errorList.add("密码长度不能小于" + USER_PASSWORD_LENGTH);
            user.setUserPassword(userPassword);
        }

        if(errorList.size() > 0)
            return new ServerResponse<User>(0,errorList.toString(),user);

        return userService.UserRegister(user);
    }




}
