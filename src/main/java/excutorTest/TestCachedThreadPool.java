package excutorTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService 的 execute用法
 *
 */
public class TestCachedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new TestRunnable());
            System.out.println("************* a" + i + " *************");
        }
        executorService.shutdown();
    }

    static class TestRunnable implements Runnable {
        public void run() {
            //程序运行后可以明显看到时在并发的调用
            System.out.println(Thread.currentThread().getName() + "线程被调用了。");
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

