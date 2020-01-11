package lllr.test.user;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

interface D{
    void say3();
}


abstract class B implements D{
    private int num ;
    B(int num){this.num = num;}
    B(){};

    public int getNum(){return num;}
    public void say(){System.out.println("say");}
    private void say1(){System.out.println("say1");}
    public abstract void say2();
    public void say3(){System.out.println("say3");}
        };

class C extends B{

    @Override
    public void say2() {
        System.out.println("say2");
    }

}


public class UserTest {

    @Test
    public void test11(){
        C c = new C();
        c.say2();
        c.say();
        c.say3();
    }

    public class A implements Cloneable{
        private int value;

        A(){value = 0;}
        A(int value){this.value = value;}

        public int getValue(){return value;}
        public void setValue(int value){this.value = value;}
        public boolean equals(Object object){
            if(this == object)
                return true;
            if(!(object instanceof A))
                return false;
            if(((A) object).getValue() == value)
                return true;
            return false;
        }

        public int hashCode(){
            return 17 * 37 + value;
        }
        public Object clone() throws CloneNotSupportedException{
            return super.clone();
        }
    }
    private static final int[] array = {1,2,3,4};
    public static final List VALUE = Collections.unmodifiableList(Arrays.asList(1,2,3,4));

    public static final int[] values(){
        return array.clone();
    }

    public int[] getArray(){
        return array;
    }

    @Test
    public void test9(){
        int[] copy = values();
        copy[0] = 0;
        System.out.println(getArray()[0]);
        System.out.println(copy[0]);
    }

    @Test
    public void test8(){
        System.out.println(VALUE.size());
    }

    @Test
    public void test6() throws CloneNotSupportedException {
        A a = new A();
        A a1 = (A) a.clone();
        System.out.println(a == a1);
    }


    @Test
    public void test4() {
        A a = new A();
        A a1 = new A(1);
        A a2 = new A(1);
        System.out.println(a.equals(a1));
        System.out.println(a1.equals(a2));
        System.out.println(a.hashCode());
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());

        Map map = new HashMap();
        map.put(a,"a");
        map.put(a1,"a1");
        map.put(a2,"a2");

        String temp = (String) map.get(new A());
        String temp1 = (String)map.get(a);
        System.out.println(temp);
        System.out.println(temp1);
    }

    @Test
    public void test3() {
        String string = "str";
        String string1 = "str1";
        System.out.println(string.hashCode());
        System.out.println(string1.hashCode());
        Map map = new HashMap();
        map.put(string,"1");
        map.put(string1,"2");
        System.out.println(map.get("str"));
        System.out.println("str".hashCode());
        System.out.println(new String("str").hashCode());
    }
    @Test
    public void test2() {
        Object object = new Object();
        Object object1 = new Object();
        String string = "string";
        System.out.println(object.hashCode());
        System.out.println(object1.hashCode());
        System.out.println(string.hashCode());

    }
    @Test
    public void test1() {
        Integer integer = new Integer("1");
        Integer integer1 = new Integer("1");
        Integer integer2 = new Integer("2");
        System.out.println(integer.equals(integer1));
        System.out.println(integer.equals(integer2));
    }
    @Test
    public void test(){
            Object object1 = new Object();
            Object object2 = new Object();
            System.out.println(object1);
            System.out.println(object2);
            boolean isSame = object1.equals(object2);
            System.out.println(isSame);
        String str = "123";
        String str1 = "123";
        System.out.println(str.equals(str1));
        System.out.println(str);
        System.out.println(str1);
        String str2 = new String("123");
        System.out.println(str2.equals(str1));

    }

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
