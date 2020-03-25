package lllr.test.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class ReentrantLock {
    private static Unsafe unsafe;
    private volatile Thread currentThread;
    private static long curThreadOffset;

    static {
        try {
            Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            unsafe = constructor.newInstance();
            Field curThreadField = ReentrantLock.class.getDeclaredField("currentThread");
            curThreadOffset = unsafe.objectFieldOffset(curThreadField);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public ReentrantLock(){

    }

    private volatile List<Thread> threadList = new ArrayList<>();

    private void addThread(Thread thread){
        threadList.add(thread);
    }

    private void awakeThread(){
        if(threadList.size() == 0)
        {
            System.out.println("线程数组的长度为0，结束!");
            return;
        }
        //线程数组不能为空 ， 唤醒一个线程继续运行

        Thread awake = threadList.remove(0);

        LockSupport.unpark(awake);

        System.out.println("唤醒线程：" + awake);

    }



    public void lock(){
        System.out.println(Thread.currentThread() + "  lock  thread  " + currentThread);

        //阻塞线程
        while(currentThread != null) {
            addThread(Thread.currentThread());
            System.out.println(Thread.currentThread() + "获取不到锁，阻塞");

            LockSupport.park();
        }

        System.out.println(Thread.currentThread() + "获取得到锁，继续运行");
        currentThread = Thread.currentThread();

    }

    public void unlockBySyn(){
        System.out.println(Thread.currentThread() + "  unlock");

        currentThread = null;
        //唤醒另一个线程
        awakeThread();

    }

    public void unlockByUns(){
        System.out.println(Thread.currentThread() + "  unlock");

        System.out.println(currentThread + "  " + curThreadOffset);
        //设置 currentThread 为 null
        unsafe.getAndSetObject(this,curThreadOffset,null);
        //唤醒另一个线程
        awakeThread();

    }
}
