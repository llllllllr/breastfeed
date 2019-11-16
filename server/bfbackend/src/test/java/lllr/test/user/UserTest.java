package lllr.test.user;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class UserTest {
    @Test
    public void fun(){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

    @Test
    public void fun1(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        System.out.println(uuid + uuid.length());
    }
}
