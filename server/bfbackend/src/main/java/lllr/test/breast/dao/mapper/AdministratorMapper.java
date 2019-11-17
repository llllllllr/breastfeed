package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.user.Administrator;

import java.util.List;

public interface AdministratorMapper {
    int deleteByPrimaryKey(Integer administratorId);

    int insert(Administrator record);

    int insertSelective(Administrator record);

    Administrator selectByPrimaryKey(Integer administratorId);

    int updateByPrimaryKeySelective(Administrator record);

    int updateByPrimaryKey(Administrator record);

    List<Administrator> selectByAdministratorName(String administratorName);

    List<Administrator> selectByAdministratorToken(String administratorToken);

}