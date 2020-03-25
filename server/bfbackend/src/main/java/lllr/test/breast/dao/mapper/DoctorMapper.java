package lllr.test.breast.dao.mapper;

import lllr.test.breast.dataObject.user.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKey(Doctor record);

    Doctor selectByUserName(String userName);

    //查询返回结果可能是 List  用单个对象接收多个对象会发生错误吗？
    Doctor selectByToken(String doctorToken);

    List<Doctor> selectAllDoctor();

    Doctor selectByLicenseNumber(String licenseNumber);

    int updatePasswordByLicenseNumber(@Param(value = "licenseNumber") String licenseNumber,@Param(value = "password") String password);
}