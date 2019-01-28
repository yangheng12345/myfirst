package sort;

/**
 * 归并排序
 * 时间复杂度：O(nlgn)
 */
public class MergeSort {
    public static void merSort(int[] arr, int left, int right) {

        if (left < right) {
            int mid = (left + right) / 2;
            //左边归并排序，使得左子序列有序
            merSort(arr, left, mid);
            //右边归并排序，使得右子序列有序
            merSort(arr, mid + 1, right);
            //合并两个子序列
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        //ps：也可以从开始就申请一个与原数组大小相同的数组，因为重复new数组会频繁申请内存
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        //将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        //将右序列剩余元素填充进temp中
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        //将temp中的元素全部拷贝到原数组中
        for (int k2 = 0; k2 < temp.length; k2++) {
            arr[k2 + left] = temp[k2];
        }
    }

    public static void main(String args[]) {
        int[] test = {9, 2, 6, 3, 5, 7, 10, 11, 12};
        merSort(test, 0, test.length - 1);
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i] + " ");
        }
    }
}
