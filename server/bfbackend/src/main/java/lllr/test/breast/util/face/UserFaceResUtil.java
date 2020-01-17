package lllr.test.breast.util.face;

public class UserFaceResUtil {
    public static UserFaceRes success(String message){return new UserFaceRes(1,message);}
    public static UserFaceRes error(String message){ return new UserFaceRes(0,message);}


}
