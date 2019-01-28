package sort;

/**
 * 和冒泡排序超级相似，但是这个是找到最小的才交换值，冒泡排序不是这样的，冒泡是没找到一个元素就开始冒泡比较
 * 算法复杂度：o（n2）
 */
public class SelectionSort {

    public static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int k = i;
            // 找出最小值的下标
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            // 将最小值放到未排序记录的第一个位置
            if (k > i) {
                int tmp = a[i];
                a[i] = a[k];
                a[k] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] b = {49, 38, 65, 97, 76, 13, 27, 50};
        selectionSort(b);
        for (int i : b) {
            System.out.print(i + " ");
        }
    }
}
