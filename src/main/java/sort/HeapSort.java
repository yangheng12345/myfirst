package sort;

/**
 * 堆排序是选择排序的一种，对排序只是把选择排序刚开始比较的结果记录下来了，这样就少了比较
 * https://www.cnblogs.com/MOBIN/p/5374217.html
 * 最差的情况是，nlog2n
 */
public class HeapSort {
    int[] arr;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        HeapSort heapSor = new HeapSort();
        //0下标放的是数组长度，
        int[] arr = {7, 23, 45, 9, 40, 73, 12, 49};
        heapSor.arr = arr;
        heapSor.heapSort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(".." + arr[i]);
        }
    }

    void heapAdjust(int[] arr, int s, int m) {
        //已知arr[s...m]中记录的关键字除arr[s]之外均满足堆的定义，本函数调整arr[s]的关键字，使arr[s...m]成为一个最大堆
        //s是最后一个非叶子节点
        int rc = arr[s];

        for (int j = 2 * s; j <= m; j = s * 2) {
            if (j < m && arr[j] < arr[j + 1]) {
                //j为key较大的下标
                j++;
            }
            if (rc >= arr[j]) {
                break;
            }
            //上移到父节点
            arr[s] = arr[j];
            s = j;
        }
        //要放入的位置
        arr[s] = rc;

    }

    void heapSort(int[] arr) {
        //对数组进行建堆操作，就是从最后一个非叶结点进行筛选的过程
        //因为0没有使用，所以length-1
        for (int i = (arr.length - 1) / 2; i > 0; i--) {
            heapAdjust(arr, i, arr.length - 1);
        }
        System.out.println("........建堆完成.............");

        outputArr(1);
        for (int i = arr.length - 1; i > 1; i--) {
            int temp = arr[1];
            arr[1] = arr[i];
            arr[i] = temp;
            heapAdjust(arr, 1, i - 1);
        }
    }

    void outputArr(int i) {

        if (i <= arr[0]) {
            System.out.println(arr[i]);
            //左
            outputArr(i * 2);
            //右
            outputArr(i * 2 + 1);

        }
    }
}
