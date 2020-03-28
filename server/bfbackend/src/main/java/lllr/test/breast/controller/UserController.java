package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.redis.RedisService;
import lllr.test.breast.redis.UserKey;
import lllr.test.breast.service.inter.UserService;
import lllr.test.breast.util.DataValidateUtil;
import lllr.test.breast.util.MD5Util;
import lllr.test.breast.util.exception.StringException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.Int;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Validated
@ResponseBody
@RequestMapping("/user")
@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String  COOLIE_NAME_TOKEN = "token";
    @Autowired
    private UserService userService;

    @Autowired
    MD5Util md5Util;

    @Autowired
    DataValidateUtil dataValidateUtil;

    @Autowired
    RedisService redisService;

    @GetMapping("/findPassword")
    public ServerResponse UserFindPasswordByCreditId(@RequestParam(value = "licenseNumber")@Length(min=18,max=18,message = "请输入合理的身份证")String licenseNumber,
                                                     @RequestParam(value = "password")@Length(min=6,message = "密码长度错误") String password){
        return userService.UserFindPasswordByCreditId(licenseNumber,password);
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
    public ServerResponse<User> UserRegister(@RequestParam(value = "age", required = false)@Range(min=18,max=110,message = "请输入合理的年龄") Integer age,
                                             @RequestParam(value = "creditId")@Length(min=18,max=18,message = "请输入合理的身份证")String creditId,
                                             @RequestParam(value = "pregnantType",required = false) Integer pregnantType,
                                             @RequestParam(value = "pregnantWeek",required = false) String pregnantWeek,
                                             @RequestParam(value = "job", required = false) String job,
                                             @RequestParam(value = "confinementDate",required = false) String confinementDate,
                                             @RequestParam(value = "confinementWeek",required = false) Integer confinementWeek,
                                             @RequestParam(value = "confinementType",required = false) Integer confinementType,
                                             @RequestParam(value = "userName")@NotEmpty(message = "用户名不能为空") String userName,
                                             @RequestParam(value = "userPassword")@Length(min=6,message = "密码长度错误") String userPassword,
                                             @RequestParam(value = "openId",required = false)String openId,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws StringException, ParseException {

        User user = new User();
        user.setAge(age);
        user.setOpenId(openId);
        user.setCreditId(creditId);
        user.setPregnantType(pregnantType);
        user.setPregnantWeek(pregnantWeek);
        user.setJob(job);
        if(confinementWeek != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(confinementDate);
            user.setConfinementWeek(confinementWeek);
            user.setConfinementType(confinementType);
        }
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserToken(UUID.randomUUID().toString().replace("-", ""));
        return userService.userRegister(user);
    }

    //在登录成功或者注册成功
    //在Header中添加user_token
    //在session中加入用户信息
    private void AfterSign(HttpServletRequest request, HttpServletResponse response, User user) {
        redisService.set(UserKey.token,user.getUserToken(),user.getUserId());

        //将 token 放入 header
        response.setHeader("user_token",user.getUserToken());
        response.setHeader("user_token_date", String.valueOf(System.currentTimeMillis() + 60 * 60 * 24 * 1000)); //设置token 过期时间

//
//        Cookie cookie = new Cookie(COOLIE_NAME_TOKEN,user.getUserToken());
//        cookie.setMaxAge(UserKey.expireSnds);
//        cookie.setPath("/user");
//        response.addCookie(cookie);
    }


    @GetMapping("/sign")
    public ServerResponse<User> UserSign(@RequestParam(value = "userName")@NotEmpty(message = "用户名不能为空") String userName,
                                     @RequestParam(value = "userPassword")@Length(min=6,message = "密码错误") String userPassword,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {

        System.out.println(userPassword);
        //根据用户名查询用户信息并返回
        ServerResponse<User> reData = userService.userSign(userName, userPassword);
        //不为空说明表单数据正确
        if (reData.getData() != null)
            AfterSign(request, response, reData.getData());

        return reData;
    }

    //持续化user_token免登录
    @RequestMapping("/tokenSign")
    public ServerResponse<User> UserTokenSign(@RequestParam(value = "user_token")@NotEmpty String user_token,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ServerResponse<User> reData = userService.userTokenSign(user_token);
        if(reData.getData() != null)
            AfterSign(request, response, reData.getData());
        return reData;
    }

    //用户退出
//    @GetMapping("/exit")
//    public ServerResponse exit(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().removeAttribute("userName");
//        //将浏览器的user_token 置空
//        Cookie cookie = new Cookie("user_token", null);
//        cookie.setPath("/user/tokenSign");
//        response.addCookie(cookie);
//
//        return ServerResponse.createBysuccess();
//    }


    @GetMapping("/check")
    public ServerResponse<Integer> check(HttpServletRequest request)
    {
        String cookie = request.getHeader("cookie");
        LOGGER.info(cookie);
        Integer userId = redisService.get(UserKey.token,cookie,Integer.class);

        return ServerResponse.createBysuccessData(userId);
    }


    @RequestMapping("/openId")
    public  ServerResponse<String> getOpenId(@RequestParam("userId") Integer userId)
    {
        return userService.getOpenId(userId);
    }










}
