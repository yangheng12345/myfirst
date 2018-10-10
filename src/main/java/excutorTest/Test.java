package excutorTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Set<Callable<String>> callables = new HashSet<Callable<String>>();
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    return "Task 1";
                }
            });
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep(1000);
                    return "Task 2222";
                }
            });
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    return "Task 3";
                }
            });
//            String result = executorService.invokeAny(callables);
            List<Future<String>> futures = executorService.invokeAll(callables);
            if(futures!= null && futures.size() > 0){
                for (Future future : futures){
                    System.out.println(future.get());
                }
            }
//            System.out.println("result = " + result);
            executorService.shutdown();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
