package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.inter.UserService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.exception.StringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@ResponseBody
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
    public ServerResponse<User> UserRegister(@RequestParam(value = "age", required = false) Integer age,
                                             @RequestParam(value = "creditId") String creditId,
                                             @RequestParam(value = "pregnantType") Integer pregnantType,
                                             @RequestParam(value = "pregnantWeek") String pregnantWeek,
                                             @RequestParam(value = "job", required = false) String job,
                                             @RequestParam(value = "confinementDate") String confinementDate,
                                             @RequestParam(value = "confinementWeek") Integer confinementWeek,
                                             @RequestParam(value = "confinementType") Integer confinementType,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userPassword") String userPassword,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws StringException {

        List<String> errorList = new ArrayList<>();
        User user = new User();
        if (DataValidateUtil.ageValidate(age)) {
            user.setAge(age);
        } else {
            user.setAge(null);
        }

        if (DataValidateUtil.length(creditId, 5, 0)) {
            user.setCreditId(creditId);
        } else {
            errorList.add("身份证号码错误!");
        }

        if (!DataValidateUtil.isNull(pregnantType))
            user.setPregnantType(pregnantType);
        else {
            errorList.add("怀孕类型不能为空！");
        }

        if (!DataValidateUtil.isBlank(pregnantWeek)) {
            user.setPregnantWeek(pregnantWeek);
        } else {
            errorList.add( "孕周不能为空！");
        }

        user.setJob(job);

        if (!DataValidateUtil.isNull(confinementDate)) {
            user.setConfinementDate(new Date(Long.parseLong(confinementDate)));
        } else {
            errorList.add("产期不能为空！");
        }

        if (!DataValidateUtil.isNull(confinementWeek))
            user.setConfinementWeek(confinementWeek);
        else {
            errorList.add("产周类型不能为空！");
        }

        if (!DataValidateUtil.isNull(confinementType))
            user.setConfinementType(confinementType);
        else {
            errorList.add("产期类型不能为空！");
        }

        if (!DataValidateUtil.isBlank(userName)) {
            user.setUserName(userName);
        } else {
            errorList.add("用户名不能为空！");
        }

        if (DataValidateUtil.length(userPassword, USER_PASSWORD_LENGTH, 1)) {
            user.setUserPassword(userPassword);
        } else {
            errorList.add( "密码长度不能小于" + USER_PASSWORD_LENGTH);
        }

        if (errorList.size() > 0)
            return ServerResponse.createByErrorMsgAndData(errorList.toString(), user);

        user.setUserToken(UUID.randomUUID().toString().replace("-", ""));
        ServerResponse<User> userResponse = userService.userRegister(user);

        //注册成功

        if (userResponse.getStatus() == 1)
            AfterSign(request, response, user);

        return userResponse;
    }

    //在登录成功或者注册成功
    //在cookie中添加user_token
    //在session中加入用户信息
    private void AfterSign(HttpServletRequest request, HttpServletResponse response, User user) {
        request.getSession().setAttribute("userName", user.getUserName());
        Cookie user_token_cookie = new Cookie("user_token", user.getUserToken());
        user_token_cookie.setMaxAge(10 * 60);
        user_token_cookie.setPath("/user/tokenSign");
        response.addCookie(user_token_cookie);
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
    public ServerResponse<User> UserSign(@RequestParam(value = "userName", required = true) String userName,
                                     @RequestParam(value = "userPassword", required = true) String userPassword,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {
        if (DataValidateUtil.isBlank(userName) || DataValidateUtil.isBlank(userPassword))
            return ServerResponse.createBysuccessMsg("用户名和密码不能为空");

        //根据用户名查询用户信息并返回
        ServerResponse<User> reData = userService.userSign(userName, userPassword);
        //不为空说明表单数据正确
        if (reData.getData() != null)
            AfterSign(request, response, reData.getData());

        return reData;
    }

    //持续化user_token免登录
    @RequestMapping("/tokenSign")
    public ServerResponse<User> UserTokenSign(@CookieValue(name = "user_token") String user_token,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        //判断用户是否登录
        String userName = (String) request.getSession().getAttribute("userName");

        if(userName != null)
            return ServerResponse.createBysuccess();
        if (!DataValidateUtil.isBlank(user_token)) {
            ServerResponse<User> reData = userService.userTokenSign(user_token);
            AfterSign(request, response, reData.getData());
            return reData;
        }
        return ServerResponse.createByError();
    }

    //用户退出
    @GetMapping("/exit")
    public ServerResponse exit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("userName");
        //将浏览器的user_token 置空
        Cookie cookie = new Cookie("user_token", null);
        cookie.setPath("/user/tokenSign");
        response.addCookie(cookie);

        return ServerResponse.createBysuccess();
    }

}
