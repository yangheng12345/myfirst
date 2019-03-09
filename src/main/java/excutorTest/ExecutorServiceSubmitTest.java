package excutorTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * ExecutorService 的 submit 的用法
 * submit 方法调用后程序就开始执行
 * submint 可以抛出异常  ,当调用get方法的时候，一定要记得捕捉异常，如果没有捕捉，那么程序就会卡死
 *
 *
 *  参考教程：
 *      https://blog.csdn.net/xiaojiahao_kevin/article/details/51735518  work是什么
 *      https://www.cnblogs.com/trust-freedom/p/6681948.html   源码解析ThreadPoolExecutor
 *
 *      如果核心没有执行就放入阻塞队列中
 *
 * 线程池创建过程：
 *      work数量就是核心线程池的数量，然后通过AbstractExecutorService 中的 execute 方法，每次都进行addwork，添加进去的分别是
 *      线程池中的每个线程，然后并发的执行每个线程，通过work的runwork方法，调用每个方法的run方法。
 *
 *      添加work的策略：
 *          1、小于核心数量，添加work，
 *          2、队列满了，核心的也满了，然后进行扩展至最大线程数
 *          3、如果都满了，尝试进行执行，如果执行失败，那么进行拒绝策略
 *
 *    对象之间的关系，需要多了解
 *    RunnableFuture是接口  FutureTask是具体的实现   ExecutorService是多线程接口，AbstractExecutorService 的作用是什么呢，这里
 *    真正的实现是ThreadPoolExecutor
 *
 *
 除了基础名词外需要了解一下线程池的名词：
 excutor
 abstartExcutorService
 ThreadPoolexecutor
 futureTask  future runable callable Thread  ，QueueingFuture

 阅读源码：简单说明，线程池也是通过启动多个线程并发的调用

 ExecutorService调用他的execute方法，方法生成work实例，通过线程数进行判断是否需要直接新建，放入队列，扩展到最大线程数，其中work包含Thread属性，运行work后调用runWork方法，runWork调用我们包装好的
 FutureTask的run方法。
 run方法调用的是callable的call方法（再转化为futureTask的，通过适配器都转化为了callable，方法 Executors.callable(runnable, result);重要的是方法的重写）

 CompletionService 和ExecutorService的却别：
 CompletionService 把ExecutorService进行包装，其中定义的类QueueingFuture继承了FutureTask方法，CompletionService拥有自己的队列completionQueue，重写FutureTask的done（）方法，把完成的放入队列中，
 然后通过task方法进行取出后future，然后进行get方法。
 task方法有阻塞，get方法无阻塞。

 Volatile变量：
 见性：
 　　可见性是一种复杂的属性，因为可见性中的错误总是会违背我们的直觉。通常，我们无法确保执行读操作的线程能适时地看到其他线程写入的值，有时甚至是根本不可能的事情。为了确保多个线程之间对内存写入操作的可见性，必须使用同步机制。
 　　可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。也就是一个线程修改的结果。另一个线程马上就能看到。比如：用volatile修饰的变量，就会具有可见性。volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的。但是这里需要注意一个问题，volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性。比如 volatile int a = 0；之后有一个操作 a++；这个变量a具有可见性，但是a++ 依然是一个非原子操作，也就是这个操作同样存在线程安全问题。
 　　在 Java 中 volatile、synchronized 和 final 实现可见性。
 原子性：
 　　原子是世界上的最小单位，具有不可分割性。比如 a=0；（a非long和double类型） 这个操作是不可分割的，那么我们说这个操作时原子操作。再比如：a++； 这个操作实际是a = a + 1；是可分割的，所以他不是一个原子操作。非原子操作都会存在线程安全问题，需要我们使用同步技术（sychronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。java的concurrent包下提供了一些原子类，我们可以通过阅读API来了解这些原子类的用法。比如：AtomicInteger、AtomicLong、AtomicReference等。
 　　在 Java 中 synchronized 和在 lock、unlock 中操作保证原子性。
 有序性：
 　　Java 语言提供了 volatile 和 synchronized 两个关键字来保证线程之间操作的有序性，volatile 是因为其本身包含“禁止指令重排序”的语义，synchronized 是由“一个变量在同一个时刻只允许一条线程对其进行 lock 操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。
 https://www.cnblogs.com/zhengbin/p/5654805.html
 http://www.importnew.com/15842.html

 Atomic包：
 AtomicInteger的CAS原理：乐观锁悲观锁   CAS就是Compare and Swap UnSafe
 https://blog.csdn.net/qfycc92/article/details/46489553
 CAS
 https://blog.csdn.net/mmoren/article/details/79185862

 lock源码查看：
 lock底层原理：https://blog.csdn.net/Luxia_24/article/details/52403033
 公平锁，非公平锁，自旋锁
 *
 */
public class ExecutorServiceSubmitTest {
    public static void main(String[] args) throws  Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        //创建10个任务并执行
        for (int i = 0; i < 30; i++) {
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            //将任务执行结果存储到List中
            resultList.add(future);
        }

        //遍历任务的结果
        try{
            for (Future<String> fs : resultList) {
                System.out.println(fs.get());
                try {
                    System.out.println(fs.get());     //打印各个线程（任务）执行的结果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                    //executorService.shutdown();
                }
            }
        }catch (Exception e){

        }finally {
            executorService.shutdown();
        }


    }

    static class TaskWithResult implements Callable<String> {
        private int id;

        public TaskWithResult(int id) {
            this.id = id;
        }

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
         *
         * @return
         * @throws Exception
         */
        @Override
        public String call() throws Exception {
            System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
            if (new Random().nextBoolean()){
                throw new TaskException("Meet error in task." + Thread.currentThread().getName());
            }


            //一个模拟耗时的操作
            //for (int i = 999999; i > 0; i--) {};
            Thread.sleep(1000);
            return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
        }
        class TaskException extends Exception {
            public TaskException(String message) {
                super(message);
            }
        }

    }

    /**
     * excute 和 submit的区别：
     *
     */
}


