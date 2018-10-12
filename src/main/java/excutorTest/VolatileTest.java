package excutorTest;

public class VolatileTest {
    volatile static int someValue;

    public static void main(String[] args) {
        VolatileTest();
    }

    /**
     * volatile,a++,是三个指令，所以有的时候输出的结果是1哦，但是对于多个线程来说他们是相同的，可以用来做开关之类
     * refer：
         https://www.cnblogs.com/zhengbin/p/5654805.html
         http://www.importnew.com/15842.html
     */
    private static void VolatileTest() {
        System.out.println(someValue++);
    }
}
