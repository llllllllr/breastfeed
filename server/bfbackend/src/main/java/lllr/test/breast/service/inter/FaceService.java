package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.util.exception.FaceException;

import java.io.File;
import java.io.IOException;

public interface FaceService {
    ServerResponse FaceSign(File image) throws FaceException;        //人脸登录

    ServerResponse FaceRegister(File image) throws FaceException, IOException;       //人脸注册

}
