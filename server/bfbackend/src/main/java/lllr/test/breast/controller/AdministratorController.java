package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Administrator;
import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.service.inter.AdministratorService;
import lllr.test.breast.util.DataValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

@ResponseBody
@Controller
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    //管理员登录
    @GetMapping("/administrator/sign")
    public ServerResponse<Administrator> administratorSign(@RequestParam(value = "administratorName")@NotEmpty(message = "用户名不能为空") String administratorName,
                                                    @RequestParam(value = "administratorPassword")@NotEmpty(message = "密码不能为空") String administratorPassword,
                                                    HttpServletResponse response,
                                                    HttpServletRequest request) {

        //根据用户名查询用户信息并返回
        ServerResponse<Administrator> reData = administratorService.administratorSign(administratorName, administratorPassword);
        //不为空说明表单数据正确
        if (reData.getData() != null)
            AfterSign(request, response, reData.getData());
        return reData;
    }

    //在登录成功或者注册成功
    //在cookie中添加user_token
    //在session中加入用户信息
    private void AfterSign(HttpServletRequest request, HttpServletResponse response, Administrator administrator) {
//        Cookie administrator_token_cookie = new Cookie("administrator_token", administrator.getAdministratorToken());
//        administrator_token_cookie.setMaxAge(10 * 60 * 1000);
//        administrator_token_cookie.setPath("/administrator");
//        response.addCookie(administrator_token_cookie);

        response.setHeader("administrator_token",administrator.getAdministratorToken());
        response.setHeader("administrator_token_date",String.valueOf(System.currentTimeMillis() + 60 * 60 * 24 * 1000));
    }

    //管理员token 免密码登录
    @RequestMapping("/administrator")
    public ServerResponse<Administrator> administratorTokenSign(@RequestParam(value = "administrator_token")@NotEmpty String administrator_token,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        ServerResponse<Administrator> reData = administratorService.administratorTokenSign(administrator_token);
        if(reData.getData() != null)
            AfterSign(request, response, reData.getData());
        return reData;
    }


}
