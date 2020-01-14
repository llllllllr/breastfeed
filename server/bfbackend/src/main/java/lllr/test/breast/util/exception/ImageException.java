package lllr.test.breast.util.exception;


/*
    图片异常类
    处理两个异常类型：
    1.图片的类型不符合规范
    2.图片的大小超出范围

 */
public class ImageException extends Exception{
    public ImageException(){
        super();
    }

    public ImageException(String errorMessage){
        super(errorMessage);
    }

}
