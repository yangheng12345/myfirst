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
 */
public class ExecutorServiceSubmitTest {
    public static void main(String[] args) throws  Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        //创建10个任务并执行
        for (int i = 0; i < 10; i++) {
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            //将任务执行结果存储到List中
            resultList.add(future);
        }

        //遍历任务的结果
        for (Future<String> fs : resultList) {
            System.out.println(fs.get());
//            try {
//                System.out.println(fs.get());     //打印各个线程（任务）执行的结果
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } finally {
//                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
//                executorService.shutdown();
//            }
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
        public String call() throws Exception {
            System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
            if (new Random().nextBoolean())
                throw new TaskException("Meet error in task." + Thread.currentThread().getName());

            //一个模拟耗时的操作
            for (int i = 999999; i > 0; i--) ;
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


