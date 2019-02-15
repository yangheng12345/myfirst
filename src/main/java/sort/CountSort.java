package sort;

/**
 *
 * 计数排序不是基于比较的排序算法，其核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。
 * 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。--->例子里的是正整数或者0，否则要记录最小值减一下，数组下班不能为负数
 *
 * https://www.cnblogs.com/developerY/p/3166462.html
 * 累加记录出来位置，C[array[i]]--，是为了往前放置，方便存放下一个同样大小的元素，增加了空间复杂度，记录了大小顺序
 * 时间复杂度o(n)
 * 动态图演示：https://www.cnblogs.com/onepixel/articles/7674659.html
 */
public class CountSort {

    private static int[] countSort(int[] array, int k) {
        //构造C数组
        int[] C = new int[k + 1];
        //获取A数组大小用于构造B数组
        int length = array.length, sum = 0;
        //构造B数组
        int[] B = new int[length];
        for (int i = 0; i < length; i++) {
            // 统计A中各元素个数，存入C数组
            C[array[i]] += 1;
        }
        //修改C数组
        for (int i = 0; i < k + 1; i++) {
            sum += C[i];
            C[i] = sum;
        }
        //遍历A数组，构造B数组
        for (int i = length - 1; i >= 0; i--) {

            //将A中该元素放到排序后数组B中指定的位置
            B[C[array[i]] - 1] = array[i];
            //将C中该元素-1，方便存放下一个同样大小的元素
            C[array[i]]--;

        }
        //将排序好的数组返回，完成排序
        return B;

    }

    public static void main(String[] args) {
        int[] A = new int[]{2, 5, 3, 0, 2, 3, 0, 3};
        int[] B = countSort(A, 5);
        for (int i = 0; i < A.length; i++) {
            System.out.println((i + 1) + "th:" + B[i]);
        }
    }
}