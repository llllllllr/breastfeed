package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Doctor;

public interface DoctorService {
    //医生 登录 注册 token免密登录
    ServerResponse<Doctor> doctorRegister(Doctor doctor);

    ServerResponse<Doctor> doctorTokenSign(String doctor_token);

    ServerResponse<Doctor> doctorSign(String userName, String userPassword);

}
