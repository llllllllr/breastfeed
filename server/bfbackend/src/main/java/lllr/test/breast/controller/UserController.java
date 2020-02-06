package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
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

    @GetMapping("/user/register")
    public ServerResponse<User> UserRegister(@RequestParam(value = "age", required = false) Integer age,
                                             @RequestParam(value = "creditId", required = true) String creditId,
                                             @RequestParam(value = "pregnantType", required = true) Integer pregnantType,
                                             @RequestParam(value = "pregnantWeek", required = true) String pregnantWeek,
                                             @RequestParam(value = "job", required = false) String job,
                                             @RequestParam(value = "confinementDate", required = true) Date confinementDate,
                                             @RequestParam(value = "confinementWeek", required = true) Integer confinementWeek,
                                             @RequestParam(value = "confinementType", required = true) Integer confinementType,
                                             @RequestParam(value = "userName", required = true) String userName,
                                             @RequestParam(value = "userPassword", required = true) String userPassword,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws StringException {

        Map<String, String> errorMap = new HashMap<>();
        User user = new User();
        if (DataValidateUtil.ageValidate(age)) {
            user.setAge(age);
        } else {
            user.setAge(null);
        }
        if (DataValidateUtil.length(creditId, 25, 0)) {
            user.setCreditId(creditId);
        } else {
            errorMap.put("creditId", "身份证号码错误!");
            user.setCreditId(null);
        }

        if (!DataValidateUtil.isNull(pregnantType))
            user.setPregnantType(pregnantType);
        else {
            errorMap.put("pregnantType", "怀孕类型不能为空！");
            user.setCreditId(null);
        }

        if (!DataValidateUtil.isBlank(pregnantWeek)) {
            user.setPregnantWeek(pregnantWeek);
        } else {
            errorMap.put("pregnantWeek", "孕周不能为空！");
            user.setPregnantWeek(null);
        }

        user.setJob(job);

        if (!DataValidateUtil.isNull(confinementDate)) {
            user.setConfinementDate(confinementDate);
        } else {
            errorMap.put("confinementDate", "产期不能为空！");
            user.setConfinementDate(null);
        }

        if (!DataValidateUtil.isNull(confinementWeek))
            user.setConfinementWeek(confinementWeek);
        else {
            errorMap.put("confinementWeek", "产周类型不能为空！");
            user.setConfinementWeek(null);
        }

        if (!DataValidateUtil.isNull(confinementType))
            user.setConfinementType(confinementType);
        else {
            errorMap.put("confinementType", "产期类型不能为空！");
            user.setConfinementType(null);
        }

        if (!DataValidateUtil.isBlank(userName)) {
            user.setUserName(userName);
        } else {
            errorMap.put("userName", "用户名不能为空！");
            user.setUserName(userName);
        }

        if (DataValidateUtil.length(userPassword, USER_PASSWORD_LENGTH, 1)) {
            user.setUserPassword(userPassword);
        } else {
            errorMap.put("userPassword", "密码长度不能小于" + USER_PASSWORD_LENGTH);
            user.setUserPassword(userPassword);
        }

        if (errorMap.size() > 0)
            return new ServerResponse<User>(0, errorMap.toString(), user);

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
        user_token_cookie.setPath("/");
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

    @GetMapping("/user/sign")
    public ServerResponse<User> sign(@RequestParam(value = "userName", required = true) String userName,
                                     @RequestParam(value = "userPassword", required = true) String userPassword,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {
        if (DataValidateUtil.isBlank(userName) || DataValidateUtil.isBlank(userPassword))
            return new ServerResponse<>(0, "用户名和密码不能为空");

        //根据用户名查询用户信息并返回
        User user = userService.userSign(userName, userPassword);
        //不为空说明表单数据正确
        if (user != null) {
            AfterSign(request, response, user);
            return new ServerResponse<>(1, "登录成功!");
        }

        //为空说明用户名或者密码错误
        return new ServerResponse<>(0, "用户名或密码错误!");

    }

    //持续化user_token免登录
    @RequestMapping("/")
    public String userTokenSign(@CookieValue(name = "user_token", required = false) String user_token,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        //判断用户是否登录
        String userName = (String) request.getSession().getAttribute("userName");
        if (userName == null && DataValidateUtil.isBlank(user_token)) {
            User user = userService.userTokenSign(user_token);
            AfterSign(request, response, user);
        }
        return "user_token_sign_success";
    }

    //用户退出
    @GetMapping("/user/exit")
    public ServerResponse<Object> exit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("userName");
        //将浏览器的user_token 置空
        Cookie cookie = new Cookie("user_token", null);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ServerResponse<>(1, "退出登录成功!");
    }

}
