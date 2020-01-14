package lllr.test.breast.service.inter;

import lllr.test.breast.util.exception.FaceRegisterException;
import lllr.test.breast.util.exception.ImageException;

import java.io.File;

public interface FaceService {
    void FaceSign(File image) throws ImageException;        //人脸登录

    void FaceRegister(File image) throws ImageException, FaceRegisterException;       //人脸注册

}
