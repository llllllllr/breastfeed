package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.UserService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.exception.StringException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ServerResponse<User> register(@RequestParam(value="age",required = false)Integer age,
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



        return null;
    }




}
