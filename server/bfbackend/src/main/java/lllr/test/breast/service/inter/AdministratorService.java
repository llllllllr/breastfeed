package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Administrator;

public interface AdministratorService {
    ServerResponse<Administrator> administratorSign(String administratorName, String administratorPassword);    //登录

    ServerResponse<Administrator> administratorTokenSign(String administrator_token);           //token 持续化登录
}
