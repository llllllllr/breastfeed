package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.DoctorMapper;
import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.service.inter.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public ServerResponse<Doctor> doctorRegister(Doctor doctor) {
        //查询注册的 用户名 是否已经存在
        Doctor temp = doctorMapper.selectByUserName(doctor.getUserName());
        if(temp != null)
            return ServerResponse.createByErrorMsgAndData("用户名已被注册！",doctor);

        //向数据库中 插入 用户信息
        int insNum = doctorMapper.insert(doctor);
        return  insNum == 1 ? ServerResponse.createBysuccessData(doctor) : ServerResponse.createByErrorMsg("系统错误，请稍后后再试");
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
}