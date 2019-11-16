package lllr.test;

import lllr.test.breast.dataObject.user.User;
import lllr.test.breast.util.DataValidateUtil;
import org.junit.Test;

import javax.xml.crypto.Data;

public class util {
    @Test
    public void fun(){
        Object object = null;
        Object object1 = new Object();
        String string = "";
        User user = new User();
        System.out.println(DataValidateUtil.isNull(object));
        System.out.println(DataValidateUtil.isNull(object1));
        System.out.println(DataValidateUtil.isNull(string));
        System.out.println(DataValidateUtil.isNull(user));

        System.out.println(DataValidateUtil.isBlank("obj   ect"));
        System.out.println(DataValidateUtil.isBlank("object1   "));
        System.out.println(DataValidateUtil.isBlank(string));
        System.out.println(DataValidateUtil.isBlank(""));
        System.out.println(DataValidateUtil.isBlank("     "));
    }

    @Test
    public void fun1(){
        try {
            System.out.println(DataValidateUtil.length("object", 6, 0));
            System.out.println(DataValidateUtil.length("object", 6, 1));
            System.out.println(DataValidateUtil.length("object", 6, -1));
            System.out.println(DataValidateUtil.length(null, 0, 0));
            System.out.println(DataValidateUtil.length("", 0, 0));
            //System.out.println(DataValidateUtil.length("sdf", Integer.MAX_VALUE + 1, 0));
            System.out.println(DataValidateUtil.length("sdf", -1, 0));

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void fun2(){
        System.out.println(DataValidateUtil.ageValidate(-1));
        System.out.println(DataValidateUtil.ageValidate(111));
        System.out.println(DataValidateUtil.ageValidate(50));
        System.out.println(DataValidateUtil.ageValidate(0));
    }


}
