package lllr.test.breast.service.inter;

import lllr.test.breast.util.exception.FaceException;
import lllr.test.breast.util.face.UserFaceRes;

import java.io.File;
import java.io.IOException;

public interface FaceService {
    Object FaceSign(File image) throws FaceException;        //人脸登录

    Object FaceRegister(File image) throws FaceException, IOException;       //人脸注册

}
