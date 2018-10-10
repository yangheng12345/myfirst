package excutorTest;

import java.util.concurrent.Callable;

public class ReturnAfterSleepCallable implements Callable<Integer> {

    private int sleepSeconds;
    private int returnValue;

    public ReturnAfterSleepCallable(int sleepSeconds, int returnValue) {
        this.sleepSeconds = sleepSeconds;
        this.returnValue = returnValue;
    }

    @Override
    public Integer call() throws Exception {
        return 1;
    }
}
