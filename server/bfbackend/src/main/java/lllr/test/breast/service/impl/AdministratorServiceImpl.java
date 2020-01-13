package lllr.test.breast.service.Impl;

import lllr.test.breast.dao.mapper.AdministratorMapper;
import lllr.test.breast.dataObject.user.Administrator;
import lllr.test.breast.service.Inte.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public Administrator administratorSign(String administratorName, String administratorPassword) {
        List<Administrator> list = administratorMapper.selectByAdministratorName(administratorName);

        //判断密码是否正确
        if(list != null && list.size() == 1)
            if(list.get(0).getAdministratorPassword().equals(administratorPassword))
                return list.get(0);

        return null;
    }

    @Override
    public Administrator administratorTokenSign(String administrator_token) {
        List<Administrator> list = administratorMapper.selectByAdministratorToken(administrator_token);

        if(list != null && list.size() == 1)
            if(list.get(0).getAdministratorToken().equals(administrator_token))
                return list.get(0);

        return null;
    }
}
