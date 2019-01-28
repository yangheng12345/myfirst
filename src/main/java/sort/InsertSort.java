package sort;
/**
 * 直接插入排序  的实现
 * 稳定算法
 * 动态图演示：https://www.cnblogs.com/onepixel/articles/7674659.html
 * 算法复杂度：o（n2）
 */
public class InsertSort {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int a[] = {3,1,5,7,2,4,9,6};
        new InsertSort().insertSort(a);
    }
    /**
     * 直接插入排序算法的实现
     * @param a
     */
    private void insertSort(int[] a) {
        // TODO Auto-generated method stub
        System.out.println("———————————————————直接插入排序算法—————————————————————");
        int n = a.length;
        int i,j;
        for(i=1;i<n;i++){
            /**
             * temp为本次循环待插入有序列表中的数
             */
            int temp = a[i];
            /**
             * 寻找temp插入有序列表的正确位置
             */
            for(j=i-1;j>=0 && a[j]>temp;j--){
                /**
                 * 元素后移，为插入temp做准备
                 */
                a[j+1] = a[j];
            }
            /**
             * 插入temp
             */
            a[j+1] = temp;
            print(a,n,i);
        }
        printResult(a,n);
    }
    /**
     * 打印排序的最终结果
     * @param a
     * @param n
     */
    private void printResult(int[] a, int n){
        System.out.print("最终排序结果：");
        for(int j=0;j<n;j++){
            System.out.print(" "+a[j]);
        }
        System.out.println();
    }
    /**
     * 打印排序的每次循环的结果
     * @param a
     * @param n
     * @param i
     */
    private void print(int[] a, int n, int i) {
        // TODO Auto-generated method stub
        System.out.print("第"+i+"次：");
        for(int j=0;j<n;j++){
            System.out.print(" "+a[j]);
        }
        System.out.println();
    }
}