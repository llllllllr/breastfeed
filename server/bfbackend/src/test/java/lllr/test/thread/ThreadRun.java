package lllr.test.thread;

public class ThreadRun {
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public void test(){
        reentrantLock.lock();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reentrantLock.unlockBySyn();
        //reentrantLock.unlockByUns();
    }
}
