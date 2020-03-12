package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.AdministratorMapper;
import lllr.test.breast.dataObject.user.Administrator;
import lllr.test.breast.service.inter.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public ServerResponse<Administrator> administratorSign(String administratorName, String administratorPassword) {
        List<Administrator> list = administratorMapper.selectByAdministratorName(administratorName);

        //判断密码是否正确
        if (list != null && list.size() == 1)
            if (list.get(0).getAdministratorPassword().equals(administratorPassword))
                return ServerResponse.createBysuccessData(list.get(0));

        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<Administrator> administratorTokenSign(String administrator_token) {
        List<Administrator> list = administratorMapper.selectByAdministratorToken(administrator_token);

        if (list != null && list.size() == 1)
            return ServerResponse.createBysuccessData(list.get(0));

        return ServerResponse.createByError();
    }
}
