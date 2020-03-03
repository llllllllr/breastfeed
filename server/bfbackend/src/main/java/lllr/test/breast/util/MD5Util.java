package lllr.test.breast.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class MD5Util {

    private static String salt = "fdsfvxnmcvnew68sa5d54ds";

    public  static String md5(String str)
    {
        return DigestUtils.md5Hex(str);
    }

    public  static String  inputPassToFormPass(String inputPass)
    {
        String str = ""+salt.charAt(0)+salt.charAt(4) + inputPass +salt.charAt(7) + salt.charAt(4) + salt.charAt(6);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

}
