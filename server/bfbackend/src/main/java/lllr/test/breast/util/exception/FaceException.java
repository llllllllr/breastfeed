package lllr.test.breast.util.exception;

/*
  注册时 扫描人脸 异常
  异常种类:
    1.该人 已经注册了账号 ，不可重复注册
    2.在和 Face++ api 交互过程中发生错误
 */
public class FaceException extends Exception{
    public FaceException(){super();}
    public FaceException(String errorMessage){
        super(errorMessage);
    }

}
