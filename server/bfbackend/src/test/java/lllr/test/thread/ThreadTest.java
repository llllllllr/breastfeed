package lllr.test.thread;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
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

    @Test
    public void test2() throws InterruptedException {
        long time = System.currentTimeMillis();
        int a = 30;
        ThreadRun threadRun = new ThreadRun();
        Thread[] threads = new Thread[a];
        for(int i = 0 ; i < a ; i++){
            threads[i] = new Thread(){
                public void run(){
                    threadRun.test();
                }
            };

        }

        for(int i = 0 ; i < a ; i++)
            threads[i].start();

        for(int i = 0 ; i < a ; i++)
            threads[i].join();
        System.out.println("经历了:" + (System.currentTimeMillis() - time));
    }

    @Test
    public void test3()throws Exception{
        java.util.concurrent.locks.ReentrantLock reentrantLock = new java.util.concurrent.locks.ReentrantLock();
        int a = 10;
        Thread[] threads = new Thread[a];
        for(int i = 0; i < a; i++) {
            threads[i] = new Thread() {
                public void run() {
                    reentrantLock.lock();
                    System.out.println(Thread.currentThread() + "  " + reentrantLock.toString());

                    reentrantLock.unlock();
                }
            };

        }

        for(int i = 0; i < a; i++) {
            threads[i].start();
        }

        Thread.sleep(5000);

    }

    @Test
    public void test4(){
        java.util.concurrent.locks.ReentrantLock reentrantLock = new java.util.concurrent.locks.ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
    }

    @Test
    public void test5(){
        int i = 1;
        System.out.println(i<<1);
    }
}
