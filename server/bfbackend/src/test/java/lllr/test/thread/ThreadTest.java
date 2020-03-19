package lllr.test.thread;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class ThreadTest {
    @Test
    public void test() throws InterruptedException {
        System.out.println("test start");

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("thread start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                System.out.println("thread end");
            }
        };

        thread.start();
        System.out.println("test unlock thread");
        LockSupport.unpark(thread);

        System.out.println("test end");
        thread.join();
    }

    @Test
    public void test1(){
        System.out.println("test1 start");
        LockSupport.park();
        System.out.println("test1 end");
    }

}
