package lllr.test.user;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class UserTest{


    @Test
    public void test(){
        Collection[] collections = new Collection[]{new HashSet(),new ArrayList(),new HashMap().values()};
        A.a(collections[0]);
        A.a(collections[1]);
        A.a(collections[2]);
    }

    @Test
    public void test1(){
        B b = new B();
        b.aa(new HashMap());
        C c = new C();
        c.aa(new ArrayList());
    }

    @Test
    public void test2(){
        E e = new F();
        e.printB();
        D d = new F();
        d.printA();
    }

}


class B{
    public void aa(HashMap map){
        System.out.println("父类被执行！");
    }

}

class C {
    public void aa(HashMap map){
        System.out.println("父类被执行！");
    }

    public void aa(Collection map){
        System.out.println("子类被执行！");
    }
}

class A{
    public static void a(Set set){
        System.out.println("this is a set");
    }

    public static void a(List list){
        System.out.println("this is a list");
    }

    public static void a(Collection collection){
        System.out.println("unknown");
    }

}

interface D{
    void printA();
}

interface E{
    void printB();
}

class F implements D,E{

    @Override
    public void printA() {
        System.out.println("this is a");
    }

    @Override
    public void printB() {
        System.out.println("this is b");
    }
}