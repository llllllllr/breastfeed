package lllr.test.breast.service.Inte;

import lllr.test.breast.dataObject.user.Administrator;

public interface AdministratorService {
    Administrator administratorSign(String administratorName, String administratorPassword);    //登录
    Administrator administratorTokenSign(String administrator_token);           //token 持续化登录
}
