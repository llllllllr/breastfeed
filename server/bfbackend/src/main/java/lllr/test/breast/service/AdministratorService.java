package lllr.test.breast.service;

import lllr.test.breast.dataObject.user.Administrator;
import lllr.test.breast.dataObject.user.User;

public interface AdministratorService {
    Administrator administratorSign(String administratorName, String administratorPassword);    //登录
    Administrator administratorTokenSign(String administrator_token);           //token 持续化登录
}
