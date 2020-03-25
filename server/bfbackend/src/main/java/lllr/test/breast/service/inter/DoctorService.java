package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.user.Doctor;

import java.util.List;

public interface DoctorService {
    //医生 登录 注册 token免密登录
    ServerResponse<Doctor> doctorRegister(Doctor doctor);

    ServerResponse<Doctor> doctorTokenSign(String doctor_token);

    ServerResponse<Doctor> doctorSign(String userName, String userPassword);

    ServerResponse updateConsultCost(Integer doctorId, Integer consultCost);

    ServerResponse<List<Doctor>> getAllDoctor();

    ServerResponse UserFindPasswordByLicenseNumber(String licenseNumber, String password);
}
