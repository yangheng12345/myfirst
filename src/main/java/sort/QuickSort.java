package sort;

import java.util.*;
/**
 *  快速排序算法
 *  原理图
 *  https://jingyan.baidu.com/article/d45ad148905ccf69552b80d9.html
 *  理解：第一趟排序，保证轴左边的一定小于右边的值，一直递归下去，复杂度就是O(nlogn)
 *  https://blog.csdn.net/Advance_/article/details/81880509
 */
public class QuickSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] numbers = new int[n];

        for (int i=0 ; i<n;i++) {
            //循环获取数组
            numbers[i] = scanner.nextInt();
        }

        if(numbers.length>0)
        {
            Quicksort(numbers, 0, numbers.length-1);
        }

        for (int i : numbers) {
            System.out.printf(i + " ");
        }
        scanner.close();


    }

    /**
     * 获取中轴，把numbers[0]作为中轴，两侧分化，一侧比中轴小，一侧比中轴大
     * 返回中轴所在
     * @param numbers
     * @param low
     * @param high
     * @return
     */
    public static int getMiddle(int[] numbers,int low,int high)
    {
        int temp = numbers[low];
        while(low<high)
        {
            //改写为<=可以实现从大到小排序
            while (low<high&&numbers[high]>=temp)
            {
                high--;
            }
            if(low<high)
            {
                numbers[low]=numbers[high];
            }
            //同上
            while (low<high&&numbers[low]<=temp)
            {
                low++;
            }
            if(high>low)
            {
                numbers[high]=numbers[low];
            }
        }
        numbers[low] = temp;
        return low;
    }

    /**
     * 迭代快排，将中轴的两侧再次求中轴，分化，直至low==high，完成快排
     * @param numbers
     * @param low
     * @param high
     */
    public static void Quicksort(int[] numbers,int low,int high)
    {
        if (low<high)
        {
            int middle = getMiddle(numbers, low, high);
            Quicksort(numbers, low, middle-1);
            Quicksort(numbers, middle+1, high);
        }
    }

}
