package lllr.test.breast.util;

import lllr.test.breast.util.exception.StringException;

import java.security.MessageDigest;

//string工具类
public class DataValidateUtil {

    // SHA1 加密
    public static String SHA1(String str){
        if(str == null || str.length()==0){
            return "";
        }
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return "";
        }
    }

    //判断字是否为null
    public static boolean isNull(Object str) {
        return str == null;
    }

    //是否为空
    public static boolean isBlank(String str) {
        if (isNull(str))
            return false;
        else if (str.trim().equals(""))
            return true;
        return false;
    }

    //判断字符的长度
    //flag为-1：字符串长度小于期望值
    //flag为0：字符串长度等于期望值
    //flag为1：字符串长度大于期望值
    public static boolean length(String str, int except_length, int flag) throws StringException {
        if (except_length < 0 || except_length > Integer.MAX_VALUE)
            throw new StringException("期望长度不合理!");

        if (isNull(str))
            return false;

        if (flag == -1 && str.trim().length() < except_length)
            return true;
        if (flag == 0 && str.trim().length() == except_length)
            return true;
        if (flag == 1 && str.trim().length() > except_length)
            return true;

        return false;
    }

    //判断 年龄是否符合要求
    public static boolean ageValidate(Integer age) {
        if (age == null || age < 0 || age > 100)
            return false;
        return true;
    }
}
