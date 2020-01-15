package lllr.test.breast.service.inter;

import lllr.test.breast.util.exception.FaceException;

import java.io.File;

public interface FaceService {
    void FaceSign(File image) throws FaceException;        //人脸登录

    void FaceRegister(File image) throws FaceException;       //人脸注册

}
