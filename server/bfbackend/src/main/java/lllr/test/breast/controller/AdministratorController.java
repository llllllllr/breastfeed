package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Administrator;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.AdministratorService;
import lllr.test.breast.util.DataValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ResponseBody
@Controller
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/administrator/sign")
    public ServerResponse<Object> administratorSign(@RequestParam(value="administratorName",required = true) String administratorName,
                                 @RequestParam(value="administratorPassword",required = true) String administratorPassword,
                                 HttpServletResponse response,
                                 HttpServletRequest request){
        if(DataValidateUtil.isBlank(administratorName) || DataValidateUtil.isBlank(administratorPassword))
            return new ServerResponse<>(0,"用户名和密码不能为空");

        //根据用户名查询用户信息并返回
        Administrator administrator = administratorService.administratorSign(administratorName,administratorPassword);
        //不为空说明表单数据正确
        if(administrator != null) {
            AfterSign(request, response, administrator);
            return new ServerResponse<>(1,"登录成功!");
        }

        //为空说明用户名或者密码错误
        return new ServerResponse<>(0,"用户名或密码错误!");

    }

    //在登录成功或者注册成功
    //在cookie中添加user_token
    //在session中加入用户信息
    private void AfterSign(HttpServletRequest request, HttpServletResponse response, Administrator administrator){
        request.getSession().setAttribute("administratorName",administrator.getAdministratorName());
        Cookie administrator_token_cookie = new Cookie("administrator_token",administrator.getAdministratorToken());
        administrator_token_cookie.setMaxAge(10*60);
        administrator_token_cookie.setPath("/administrator");
        response.addCookie(administrator_token_cookie);
    }

}