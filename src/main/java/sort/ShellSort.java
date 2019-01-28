package sort;

/**
 * 希尔排序主要是有这个步长，步长到1即表示算法执行完了
 * 算法复杂度：o（n1.3）
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] ins = {2, 3, 5, 1, 23, 6, 78, 34, 23, 4, 5, 78, 34, 65, 32, 65, 76, 32, 76, 1, 9};
        int[] ins2 = sort(ins);
        for (int in : ins2) {
            System.out.println(in);
        }
    }

    public static int[] sort(int[] ins) {

        int n = ins.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int j = gap; j < n; j++) {
                int i = j;
                while (i >= gap && ins[i - gap] > ins[i]) {
                    int temp = ins[i - gap] + ins[i];
                    ins[i - gap] = temp - ins[i - gap];
                    ins[i] = temp - ins[i - gap];
                    i -= gap;
                }
            }
            gap = gap / 2;
        }
        return ins;
    }
}