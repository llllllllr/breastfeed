package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.DoctorMapper;
import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.service.inter.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public ServerResponse<Doctor> doctorRegister(Doctor doctor) {
        //查询注册的 用户名 是否已经存在
        Doctor temp = doctorMapper.selectByUserName(doctor.getUserName());
        if(temp != null)
            return ServerResponse.createByErrorMsg("用户名已被注册！");

        //查询  职业证号  是否被注册过
        temp = doctorMapper.selectByLicenseNumber(doctor.getLicenseNumber());
        if(temp != null)
            return ServerResponse.createByErrorMsg("职业证号已被注册！");

        //向数据库中 插入 用户信息
        int insNum = doctorMapper.insert(doctor);
        return  insNum == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByErrorMsg("系统错误，请稍后后再试");
    }

    @Override
    public ServerResponse<Doctor> doctorTokenSign(String doctor_token) {
        Doctor temp = doctorMapper.selectByToken(doctor_token);
        if(temp == null || !temp.getToken().equals(doctor_token))
            return ServerResponse.createByError();

        return ServerResponse.createBysuccessData(temp);
    }

    @Override
    public ServerResponse<Doctor> doctorSign(String userName, String userPassword) {
        //查询注册的 用户名 是否已经存在
        Doctor temp = doctorMapper.selectByUserName(userName);
        if(temp == null || !temp.getUserPassword().equals(userPassword))
            return ServerResponse.createByErrorMsg("账号或密码错误!");

        return ServerResponse.createBysuccessData(temp);
    }

    @Override
    public ServerResponse updateConsultCost(Integer doctorId, Integer consultCost) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        return doctorMapper.updateByPrimaryKeySelective(doctor) == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByError();
    }

    @Override
    public ServerResponse<List<Doctor>> getAllDoctor() {
        List<Doctor> doctors =  doctorMapper.selectAllDoctor();
        return doctors != null ? ServerResponse.createBysuccessData(doctors) : ServerResponse.createByError();
    }

    @Override
    public ServerResponse UserFindPasswordByLicenseNumber(String licenseNumber, String password) {
        if(doctorMapper.selectByLicenseNumber(licenseNumber) != null && doctorMapper.updatePasswordByLicenseNumber(licenseNumber,password) == 1 )
            return ServerResponse.createBysuccess();
        return ServerResponse.createByError();
    }
}
