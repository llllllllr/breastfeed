package lllr.test.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadTest {


    public volatile int num = 0;
    public void increase(){
        num++;
    }

    @Test
    public void test(){
        ThreadTest threadTest = new ThreadTest();

        for(int i = 0 ; i < 10 ; i++){
            new Thread(() -> {
                for(int j = 0 ; j < 10000 ; j++)
                    threadTest.increase();
            }).start();
        }

        while(Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(threadTest.num);

    }

    public static class CallerTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "null";
        }
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        new Thread(futureTask).start();

        String result = futureTask.get();
        System.out.println(result);

    }

    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    @Test
    public void test2() throws InterruptedException {
        threadLocal.set("test local");
        System.out.println("test  " + threadLocal.get());
        Thread.sleep(2000);
        Thread thread1 = new Thread(() -> {
            System.out.println("test2 test " + threadLocal.get());
            threadLocal.set("thread1 local");
            System.out.println("test2  " + threadLocal.get());
        });

        thread1.start();
        thread1.join();
    }

    public static class CalNum {
        public int n = 0;

    }
    public static final CalNum calNum ;

    static{
        calNum = new CalNum();
    }

    @Test
    public void test3() throws InterruptedException {

        Thread thread1 = new Thread(()->{
            for(int i = 0 ;i < 1000000 ; i++)
                synchronized (calNum) {
                    calNum.n++;
                }
        });
        Thread thread2 = new Thread(()->{
            for(int i = 0 ;i < 1000000 ; i++)
                synchronized (calNum) {
                    calNum.n++;
                }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(calNum.n);
    }

    @Test
    public void test4(){
    }


}
