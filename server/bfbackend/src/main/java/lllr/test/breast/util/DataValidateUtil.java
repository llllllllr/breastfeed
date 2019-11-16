package lllr.test.breast.util;

import lllr.test.breast.util.exception.StringException;

//string工具类
public class DataValidateUtil {
    //判断字符串是否为null
    public static boolean isNull(Object str){
        return str==null;
    }

    //是否为空
    public static boolean isBlank(String str){
        if(isNull(str))
            return false;
        else
            if(str.trim().equals(""))
                return true;
            return false;
    }

    //判断字符的长度
    //flag为-1：字符串长度小于期望值
    //flag为0：字符串长度等于期望值
    //flag为1：字符串长度大于期望值
    public static boolean length(String str,int except_length,int flag) throws StringException {
        if(except_length < 0 || except_length > Integer.MAX_VALUE)
            throw new StringException("期望长度不合理!");

        if(isNull(str))
            return false;

        if(flag == -1 && str.trim().length() < except_length)
            return true;
        if(flag == 0 && str.trim().length() == except_length)
            return true;
        if(flag == 1 && str.trim().length() > except_length)
            return true;

        return false;
    }

    public static boolean ageValidate(Integer age){
        if(age == null || age < 0 || age > 100)
            return false;
        return true;
    }
}
